package com.newagesmb.androidmvvmarchitecture.di

import android.app.Application
import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.newagesmb.androidmvvmarchitecture.BuildConfig
import com.newagesmb.androidmvvmarchitecture.data.local.preferences.DataStoreManager
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.AppApis
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.AuthorizationInterceptor
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.FlowCallAdapterFactory
import com.newagesmb.androidmvvmarchitecture.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson() = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkInterceptor: AuthorizationInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient =
        OkHttpClient.Builder().run {
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)
            }
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            callTimeout(1, TimeUnit.MINUTES)
            addInterceptor(networkInterceptor)
            build()
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://3.6.12.220/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideAppApi(
        retrofit: Retrofit
    ): AppApis = retrofit.create(AppApis::class.java)


    @Singleton
    @Provides
    fun provideDataStoreManager(
        app: Application
    ): DataStoreManager {
        return DataStoreManager(app)
    }

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(
        app: Application,
        dataStoreManager: DataStoreManager,
        @ApplicationScope externalScope: CoroutineScope
    ) = AuthorizationInterceptor(
        context = app,
        dataStoreManager = dataStoreManager,
        externalScope = externalScope
    )
//    @Singleton
//    @Provides
//    fun providNetworkUtile(app:Application)=NetworkUtils(app)
}
