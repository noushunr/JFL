package com.newagesmb.androidmvvmarchitecture.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.newagesmb.androidmvvmarchitecture.utils.Constents.TEMP_PHOTO_FILE
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {


    fun createTempImageFile(context: Context): File {
        return createTempFile(context, TEMP_PHOTO_FILE)
    }
    fun getTimestamp(): String? {
        return SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.US
        ).format(Date())
    }

    fun createImageFile(context: Context, documentType: String): File {

        val fileName: String
        val userId: Any =  System.currentTimeMillis()
//        val userId: Any = if (SessionUtils.loginSession != null && SessionUtils.loginSession!!.userId != null)
//            SessionUtils.loginSession!!.userId else System.currentTimeMillis()

        fileName = "Profile_pic_${getTimestamp()}_$userId.png"

        return createTempFile(context, fileName)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun createTempFile(context: Context, fileName: String): File {

        lateinit var fileTemp: File

        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            fileTemp = File(context.getExternalFilesDir(null), fileName)
        } else {
            fileTemp = File(context.filesDir, fileName)
        }

        if (!fileTemp.exists()) {
            try {
                fileTemp.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return fileTemp
    }


    fun overwriteFile(sourceuri: Uri, des: File) {
        val sourceFilename = sourceuri.path
        //String destinationFilename = previewFilePath;

        var bis: BufferedInputStream? = null
        var bos: BufferedOutputStream? = null

        try {
            bis = BufferedInputStream(FileInputStream(sourceFilename))
            bos = BufferedOutputStream(FileOutputStream(des, false))
            val buf = ByteArray(1024)
            bis.read(buf)
            do {
                bos.write(buf)
            } while (bis.read(buf) != -1)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bis?.close()
                bos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

    }

    fun getRealPathFromUri(contentUri: Uri?,context: Context): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor =
                context.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            cursor?.getString(column_index!!)
        } finally {
            cursor?.close()
        }
    }

//    fun setImageToFrescoDraweeView(view: SimpleDraweeView, file: File) {
//        view.controller = Fresco.newDraweeControllerBuilder()
//            .setOldController(view.controller)
//            .setImageRequest(imageRequest(file))
//            .build()
//    }

//    private fun imageRequest(file: File): ImageRequest = ImageRequestBuilder
//        .newBuilderWithSource(Uri.fromFile(file))
//        .disableDiskCache()
//        .disableMemoryCache()
//        .build()
}