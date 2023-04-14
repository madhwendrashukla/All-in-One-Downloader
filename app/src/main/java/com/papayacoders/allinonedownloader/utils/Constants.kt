@file:Suppress("DEPRECATION")

package com.papayacoders.allinonedownloader.utils

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.papayacoders.allinonedownloader.AllDownload.*
import com.papayacoders.allinonedownloader.R
import okhttp3.OkHttpClient
import okhttp3.Request

import java.lang.reflect.Type
import java.util.*


object Constants {

    //  const val TiktokApi: String = "https://api2-16-h2.musical.ly/aweme/v1/aweme/detail/"
    const val TiktokApi: String = "https://api2.musical.ly/aweme/v1/playwm/detail/"

    //  const val TiktokApiNowatermark: String = "https://nodejsapidownloader.herokuapp.com/api"
    const val TiktokApiNowatermark: String = "http://localhost/img/test.php?url="

    const val Facebook_watch_api: String =
        "https://allvideotest123.000webhostapp.com/insta/fbtest.php?url="

    //http://localhost/img/test.php?url=https://vm.tiktok.com/ZSQTnNWu/
    const val DlApisUrl3: String = "https://iphoting-yt-dl-api.herokuapp.com/api/info?url="
    const val DlApisUrl2: String = "https://dlphpapis.herokuapp.com/api/info?url="
    const val DlApisUrl: String = "https://dlphpapis21.herokuapp.com/api/info?url="

    //    const val DlApisUrl: String = "https://shafatdlapisserver.herokuapp.com/api/info?url="
    //https://dlphpapis.herokuapp.com/api/info?url=
    const val STARTFOREGROUND_ACTION =
        "com.infusiblecoder.allinonevideodownloader.action.startforeground"
    const val STOPFOREGROUND_ACTION =
        "com.infusiblecoder.allinonevideodownloader.action.stopforeground"
    const val PREF_APPNAME: String = "aiovidedownloader"
    const val PREF_CLIP: String = "tikVideoDownloader"
    const val FOLDER_NAME = "/WhatsApp/"
    const val FOLDER_NAME_Whatsappbusiness = "/WhatsApp Business/"
    const val FOLDER_NAME_Whatsapp_and11 = "/Android/media/com.whatsapp/WhatsApp/"
    const val FOLDER_NAME_Whatsapp_and11_B = "/Android/media/com.whatsapp.w4b/WhatsApp Business/"
    const val SAVE_FOLDER_NAME = "/Download/AIO_Status_Saver/"
    const val MY_ANDROID_10_IDENTIFIER_OF_FILE = "All_Video_Downloader_"
    const val videoDownloaderFolderName = "AIO_Video_Downloader"

    const val tiktokWebviewUrl = "https://www.tiktok.com/?lang=en"

    const val directoryInstaShoryDirectorydownload_videos = "Video Downloader/"
    const val directoryInstaShoryDirectorydownload_images = "Video Downloader/"
    const val directoryInstaShoryDirectorydownload_audio = "Video Downloader/"


    //TODO NOTE: Should make both false if you wqant to upload to the playstore
    const val showyoutube = true
    const val showsoundcloud = true

    //TODO NOTE: if you disable ads you can make this false
    const val show_Ads = true
    const val show_startappads = true
    const val show_earning_card_in_extrafragment = true


    var myVideoUrlIs: String? = ""
    var myPhotoUrlIs: String? = ""
    lateinit var myprogressDD: ProgressDialog


    fun startInstaDownload(context: Context, Url: String) {


        try {
            System.err.println("workkkkkkkkk 4")


            val sharedPrefsFor = SharedPrefsForInstagram(context)
            val map = sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE)
            if (map != null && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != null && map.get(
                    SharedPrefsForInstagram.PREFERENCE_USERID
                ) != "oopsDintWork" && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != ""
            ) {

                downloadInstagramImageOrVideodata(
                    context,
                    Url,
                    "ds_user_id=" + map.get(
                        SharedPrefsForInstagram.PREFERENCE_USERID
                    )
                            + "; sessionid=" + map.get(SharedPrefsForInstagram.PREFERENCE_SESSIONID)
                )
            } else {
                downloadInstagramImageOrVideodata(
                    context,
                    Url,
                    iUtils.myInstagramTempCookies
                )
            }


        } catch (e: java.lang.Exception) {
            System.err.println("workkkkkkkkk 5")
            e.printStackTrace()
        }
    }


    fun downloadInstagramImageOrVideodata(
        context: Context,
        URL: String?,
        Cookie: String?
    ) {

        myprogressDD = ProgressDialog(context)
        myprogressDD.setMessage("Loading....")
        myprogressDD.setCancelable(false)
        myprogressDD.show()

        val random1 = Random()
        val j = random1.nextInt(iUtils.UserAgentsList.size)

        object : Thread() {
            override fun run() {
                val client = OkHttpClient().newBuilder()
                    .build()
                val request: Request = Request.Builder()
                    .url(URL!!)
                    .method("GET", null)
                    .addHeader("Cookie", Cookie!!)
                    .addHeader(
                        "User-Agent",
                        iUtils.UserAgentsList[j]
                    )
                    .build()
                try {
                    val response = client.newCall(request).execute()

                    if (response.code == 200) {

                        try {
                            val listType: Type =
                                object : TypeToken<ModelInstagramResponse?>() {}.type
                            val modelInstagramResponse: ModelInstagramResponse = Gson().fromJson(
                                response.body!!.string(),
                                listType
                            )


                            if (modelInstagramResponse.modelGraphshortcode.shortcode_media.edge_sidecar_to_children != null) {
                                val modelGetEdgetoNode: ModelGetEdgetoNode =
                                    modelInstagramResponse.modelGraphshortcode.shortcode_media.edge_sidecar_to_children

                                val modelEdNodeArrayList: List<ModelEdNode> =
                                    modelGetEdgetoNode.modelEdNodes
                                for (i in 0 until modelEdNodeArrayList.size) {
                                    if (modelEdNodeArrayList[i].modelNode.isIs_video) {
                                        myVideoUrlIs = modelEdNodeArrayList[i].modelNode.video_url
                                        DownloadFileMain.startDownloading(
                                            context,
                                            myVideoUrlIs,
                                            iUtils.getVideoFilenameFromURL(myVideoUrlIs),
                                            ".mp4"
                                        )
                                        // etText.setText("");
                                        myprogressDD.dismiss()
                                        myVideoUrlIs = ""
                                    } else {
                                        myPhotoUrlIs =
                                            modelEdNodeArrayList[i].modelNode.display_resources[modelEdNodeArrayList[i].modelNode.display_resources.size - 1].src
                                        DownloadFileMain.startDownloading(
                                            context,
                                            myPhotoUrlIs,
                                            iUtils.getImageFilenameFromURL(myPhotoUrlIs),
                                            ".png"
                                        )
                                        myPhotoUrlIs = ""
                                        myprogressDD.dismiss()

                                        // etText.setText("");
                                    }
                                }
                            } else {
                                val isVideo =
                                    modelInstagramResponse.modelGraphshortcode.shortcode_media.isIs_video
                                if (isVideo) {
                                    myVideoUrlIs =
                                        modelInstagramResponse.modelGraphshortcode.shortcode_media.video_url
                                    DownloadFileMain.startDownloading(
                                        context,
                                        myVideoUrlIs,
                                        iUtils.getVideoFilenameFromURL(myVideoUrlIs),
                                        ".mp4"
                                    )
                                    myprogressDD.dismiss()

                                    myVideoUrlIs = ""
                                } else {
                                    myPhotoUrlIs =
                                        modelInstagramResponse.modelGraphshortcode.shortcode_media.display_resources[modelInstagramResponse.modelGraphshortcode.shortcode_media.display_resources.size - 1].src
                                    DownloadFileMain.startDownloading(
                                        context,
                                        myPhotoUrlIs,
                                        iUtils.getImageFilenameFromURL(myPhotoUrlIs),
                                        ".png"
                                    )
                                    myprogressDD.dismiss()

                                    myPhotoUrlIs = ""
                                }
                            }
                        } catch (e: java.lang.Exception) {
                            myprogressDD.dismiss()
                            val alertDialog = AlertDialog.Builder(context).create()
                            alertDialog.setTitle(context.getString(R.string.logininsta))
                            alertDialog.setMessage(context.getString(R.string.urlisprivate))
                            alertDialog.setButton(
                                AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel)
                            ) { dialog, _ ->
                                dialog.dismiss()
                            }
                            alertDialog.show()

                        }

                    } else {
                        myprogressDD.dismiss()
                        val alertDialog = AlertDialog.Builder(context).create()
                        alertDialog.setTitle(context.getString(R.string.logininsta))
                        alertDialog.setMessage(context.getString(R.string.urlisprivate))
                        alertDialog.setButton(
                            AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel)
                        ) { dialog, _ ->
                            dialog.dismiss()
                        }
                        alertDialog.show()
                    }
                    println("working errpr \t Value: " + response.body!!.string())
                } catch (e: Exception) {

                    try {
                        println("response1122334455:   " + "Failed1 " + e.message)
                        myprogressDD.dismiss()
                        val alertDialog = AlertDialog.Builder(context).create()
                        alertDialog.setTitle(context.getString(R.string.logininsta))
                        alertDialog.setMessage(context.getString(R.string.urlisprivate))
                        alertDialog.setButton(
                            AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel)
                        ) { dialog, _ ->
                            dialog.dismiss()
                        }
                        alertDialog.show()
                    } catch (e: Exception) {

                    }

                }
            }
        }.start()


    }


}