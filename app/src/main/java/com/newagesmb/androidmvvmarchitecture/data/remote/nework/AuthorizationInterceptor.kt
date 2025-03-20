package com.newagesmb.androidmvvmarchitecture.data.remote.nework

/** Created by Noushad on 21-08-2023.
 * Copyright (c) 2023 NewAgeSys. All rights reserved.
 * updated by Afsal on 25-08-2023
 */


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.newagesmb.androidmvvmarchitecture.data.local.preferences.DataStoreManager
import com.newagesmb.androidmvvmarchitecture.di.ApplicationScope
import com.newagesmb.androidmvvmarchitecture.ui.MainActivity
import com.newagesmb.androidmvvmarchitecture.utils.Constents
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject
import kotlin.jvm.Throws


class AuthorizationInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    @ApplicationContext private val context: Context,
    @ApplicationScope private val externalScope: CoroutineScope,
) : Interceptor {

    private var authToken: String? = null
    private var refreshToken: String? = null
    private var bearerToken: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var firstRequest = chain.request()



        authToken = runBlocking {
            dataStoreManager.getAuthTokens().first()
        }

        if (authToken?.isNotEmpty() == true) {
            firstRequest =
                chain.request().newBuilder().addHeader("x-access-token", authToken ?: "").build()
        }


        bearerToken = runBlocking {
            dataStoreManager.getToken().first()
        }

      if (!bearerToken.isNullOrEmpty()) {
          firstRequest = chain.request().newBuilder()
              .addHeader("Authorization", "Bearer " + bearerToken!!)
              .build()
      }


        requestLog(firstRequest)
        var response = chain.proceed(firstRequest)

        responseLog(response)
        var authentication = response.header("Authentication")  //("authorization")

        if (authentication != null && authentication.equals("false", true)) {

            refreshToken = runBlocking { dataStoreManager.getRefreshToken().first() }

            val builder = firstRequest.newBuilder().header("refresh-token", refreshToken!!)
                .method(firstRequest.method, firstRequest.body)

            val secondRequest = builder.build()
            requestLog(secondRequest)
            response = chain.proceed(secondRequest)
            responseLog(response)


            authentication = response.header("Authentication")
            if (authentication != null && authentication == "false") {

                runBlocking {
                    dataStoreManager.clearSession()
                    clearUserData()
                }

            }

        }


        val authToken = response.header("x-access-token")
        val refreshToken = response.header("refresh-token")

        if (refreshToken != null && authToken != null && refreshToken.isNotEmpty() && authToken.isNotEmpty()) {

            externalScope.launch(Dispatchers.IO) {

                dataStoreManager.saveAuthToken(authToken)
                dataStoreManager.saveRefreshToken(refreshToken)
            }

        }

        if (response.code == 401 || (authentication != null && authentication == "false")) {
            if (response.code == 401) {
                externalScope.launch {
                    dataStoreManager.clearSession ()
                    clearUserData()

                }

            }
        }


        return response
    }

    private fun clearUserData() {
        externalScope.launch(Dispatchers.Main) {
            try {
//                context.showToast("Unauthorised login detected, signing out!!")

                val intent = Intent(Intent.ACTION_MAIN)
                intent.putExtra(Constents.INTENT_BROADCAST, Constents.LOGOUT)
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
            } catch (e: Exception) {
            }
        }
    }

    private fun requestLog(request: Request) {
        val log = StringBuilder()
        log.append("URL {)")
        log.append(
            """
  ${String.format("%s", request.url)}"""
        )
        log.append("\n}\n")
        val header = StringBuilder()
        val headers = request.headers
        var size = headers.size
        for (i in 0 until size) {
            header.append(
                """
  ${headers.name(i)}:${headers.value(i)}"""
            )
        }
        if (header.length > 0) {
            log.append("Header {")
            log.append(header)
            log.append("\n}\n")
        }
        try {
            val body = StringBuilder()
            if (request.body != null) {
                val buffer = Buffer()
                request.newBuilder().build().body!!.writeTo(buffer)
                val req = buffer.readUtf8()
                val reqArray = req.split("&".toRegex()).toTypedArray()
                size = reqArray.size
                for (i in 0 until size) {
                    body.append(
                        """
  ${reqArray[i].replace("=".toRegex(), ":")}"""
                    )
                }
            }
            if (body.length > 0) {
                log.append("Body {")
                log.append(body)
                log.append("\n}\n")
            }
        } catch (e: IOException) {
            log.append("Body {")
            log.append("\n Invalid Body")
            log.append("\n}\n\n")
        }
        Log.i("API Request", log.toString())
    }

    private fun responseLog(response: Response) {
        val log = StringBuffer("\n")
        log.append("Header {")
        val headers = response.headers
        val size = headers.size
        for (i in 0 until size) {
            log.append(
                """
  ${headers.name(i)}:${headers.value(i)}"""
            )
        }
        log.append("\n}\n")
        try {
            val responseBody = response.body
            val contentLength = responseBody!!.contentLength()
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer
            var charset = Charset.forName("UTF-8")
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"))
            }
            if (contentLength != 0L) {
                log.append("Body {")
                log.append(
                    """
  ${buffer.clone().readString(charset)}"""
                )
                log.append("\n}")
            }
        } catch (e: Exception) {
            log.append("\nBody {")
            log.append("\n Invalid Body")
            log.append("\n}\n\n")
        }
        log.append("\n_________________________")
        Log.i("API Response", log.toString())
    }
}
