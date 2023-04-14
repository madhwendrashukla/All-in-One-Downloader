package com.papayacoders.allinonedownloader.AllDownload

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Looper
import android.text.TextUtils

import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken


import com.papayacoders.allinonedownloader.utils.Constants.startInstaDownload
import com.papayacoders.allinonedownloader.AllDownload.iUtils.ShowToast
import com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain
import com.papayacoders.allinonedownloader.AllDownload.webservices.api.RetrofitApiInterface
import com.papayacoders.allinonedownloader.AllDownload.webservices.api.RetrofitClient
import com.papayacoders.allinonedownloader.R
import com.papayacoders.allinonedownloader.utils.Constants.myPhotoUrlIs
import com.papayacoders.allinonedownloader.utils.Constants.myVideoUrlIs
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.lang.reflect.Type
import java.net.URI
import java.util.*

object Utils {

    fun DownloadVideo(context: Context, url: String) {
        lateinit var progressDralogGenaratinglink: ProgressDialog
        progressDralogGenaratinglink = ProgressDialog(context)
        progressDralogGenaratinglink.setMessage(context.resources.getString(R.string.genarating_download_link))
        progressDralogGenaratinglink.setCancelable(false)

        Log.e("myhdasbdhf urlis  ", url)


        if (url.equals("") && iUtils.checkURL(url)) {
            ShowToast(
                context, context.resources?.getString(R.string.enter_valid)
            )

        } else {


            Log.d("mylogissssss", "The interstitial wasn't loaded yet.")



            if (url.contains("instagram.com")) {
                progressDralogGenaratinglink.show()
                startInstaDownload(url, progressDralogGenaratinglink, context)

            } else if (url.contains("chingari")) {
                var myurl = url
                try {
                    myurl = iUtils.extractUrls(myurl)[0]
                } catch (e: Exception) {

                }

                DownloadVideosMain.Start(context, myurl.trim(), false)
                Log.e("downloadFileName12", myurl.trim())

            } else if (url.contains("sck.io") || url.contains("snackvideo")) {
                var myurl = url
                try {
                    myurl = iUtils.extractUrls(myurl)[0]
                } catch (e: Exception) {

                }

                DownloadVideosMain.Start(context, myurl.trim(), false)
                Log.e("downloadFileName12", myurl.trim())
            } else {
                var myurl = url
                try {
                    myurl = iUtils.extractUrls(myurl)[0]
                } catch (e: Exception) {

                }

                DownloadVideosMain.Start(context, myurl.trim(), false)
                Log.e("downloadFileName12", myurl.trim())
            }

        }
    }

    fun startInstaDownload(
        Url: String, progressDralogGenaratinglink: ProgressDialog, context: Context
    ) {


//         https://www.instagram.com/p/CLBM34Rhxek/?igshid=41v6d50y6u4w
//          https://www.instagram.com/p/CLBM34Rhxek/
//           https://www.instagram.com/p/CLBM34Rhxek/?__a=1
//           https://www.instagram.com/tv/CRyVpDSAE59/

        /*
        * https://www.instagram.com/p/CUs4eKIBscn/?__a=1
        * https://www.instagram.com/p/CUktqS7pieg/?__a=1
        * https://www.instagram.com/p/CSMYRwGna3S/?__a=1
        * https://www.instagram.com/p/CR6AbwDB12R/?__a=1
        * https://www.instagram.com/p/CR6AbwDB12R/?__a=1
        * */


        var Urlwi: String?
        try {

            val uri = URI(Url)
            Urlwi = URI(
                uri.scheme,
                uri.authority,
                uri.path,
                null,  // Ignore the query part of the input url
                uri.fragment
            ).toString()


        } catch (ex: java.lang.Exception) {
            Urlwi = ""
            progressDralogGenaratinglink.dismiss()
            ShowToast(context, context.getString(R.string.invalid_url))
            return
        }

        System.err.println("workkkkkkkkk 1122112 " + Url)

        var urlwithoutlettersqp: String? = Urlwi
        System.err.println("workkkkkkkkk 1122112 " + urlwithoutlettersqp)

        urlwithoutlettersqp = "$urlwithoutlettersqp?__a=1"
        System.err.println("workkkkkkkkk 87878788 " + urlwithoutlettersqp)


        if (urlwithoutlettersqp.contains("/reel/")) {
            urlwithoutlettersqp = urlwithoutlettersqp.replace("/reel/", "/p/")
        }

        if (urlwithoutlettersqp.contains("/tv/")) {
            urlwithoutlettersqp = urlwithoutlettersqp.replace("/tv/", "/p/")
        }
        System.err.println("workkkkkkkkk 777777 " + urlwithoutlettersqp)

        AlertDialog.Builder(context).setTitle("Select Methode").setCancelable(false)
            .setNegativeButton("Methode 1", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {


                    try {
                        System.err.println("workkkkkkkkk 4")


                        val sharedPrefsFor = SharedPrefsForInstagram(context)
                        val map = sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE)
                        if (map != null && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != null && map.get(
                                SharedPrefsForInstagram.PREFERENCE_USERID
                            ) != "oopsDintWork" && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != ""
                        ) {
                            System.err.println("workkkkkkkkk 4.7")



                            downloadInstagramImageOrVideodata_old_withlogin(
                                urlwithoutlettersqp,
                                "ds_user_id=" + map.get(
                                    SharedPrefsForInstagram.PREFERENCE_USERID
                                ) + "; sessionid=" + map.get(SharedPrefsForInstagram.PREFERENCE_SESSIONID),
                                progressDralogGenaratinglink,
                                context as Activity
                            )

                        } else {
                            System.err.println("workkkkkkkkk 4.8")


                            downloadInstagramImageOrVideodata_old(
                                urlwithoutlettersqp,
                                "",
                                context as Activity,
                                progressDralogGenaratinglink
                            )

                        }


                    } catch (e: java.lang.Exception) {
                        progressDralogGenaratinglink.dismiss()
                        System.err.println("workkkkkkkkk 5")
                        e.printStackTrace()
                        ShowToast(context, context.getString(R.string.error_occ))

                    }

                }
            }).setPositiveButton("Methode 2", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    try {
                        System.err.println("workkkkkkkkk 4")


                        val sharedPrefsFor = SharedPrefsForInstagram(context)
                        val map = sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE)
                        if (map != null && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != null && map.get(
                                SharedPrefsForInstagram.PREFERENCE_USERID
                            ) != "oopsDintWork" && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != ""
                        ) {
                            System.err.println("workkkkkkkkk 5.2")




                            downloadInstagramImageOrVideodata_withlogin(
                                urlwithoutlettersqp,
                                "ds_user_id=" + map.get(
                                    SharedPrefsForInstagram.PREFERENCE_USERID
                                ) + "; sessionid=" + map.get(SharedPrefsForInstagram.PREFERENCE_SESSIONID),
                                context as Activity,
                                progressDralogGenaratinglink
                            )

                        } else {

                            System.err.println("workkkkkkkkk 4.5")

                            downloadInstagramImageOrVideodata(
                                urlwithoutlettersqp,
                                iUtils.myInstagramTempCookies,
                                context as Activity,
                                progressDralogGenaratinglink
                            )

                        }


                    } catch (e: java.lang.Exception) {
                        progressDralogGenaratinglink.dismiss()
                        System.err.println("workkkkkkkkk 5.1")
                        e.printStackTrace()
                        ShowToast(context, context.getString(R.string.error_occ))

                    }
                }
            }).show()


    }


    fun downloadInstagramImageOrVideodata_old(
        URL: String?,
        Cookie: String?,
        context: Activity,
        progressDralogGenaratinglink: ProgressDialog
    ) {
        val random1 = Random()
        val j = random1.nextInt(iUtils.UserAgentsList.size)
        object : Thread() {
            override fun run() {
                Looper.prepare()
                val client = OkHttpClient().newBuilder().build()
                val request: Request =
                    Request.Builder().url(URL!!).method("GET", null).addHeader("Cookie", Cookie!!)
                        .addHeader(
                            "User-Agent", iUtils.UserAgentsList[j]
                        ).build()
                try {
                    val response = client.newCall(request).execute()

                    System.err.println("workkkkkkkkk 6 " + response.code)

                    if (response.code == 200) {

                        try {
                            val listType: Type =
                                object : TypeToken<ModelInstagramResponse?>() {}.type
                            val modelInstagramResponse: ModelInstagramResponse = Gson().fromJson(
                                response.body!!.string(), listType
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


                                        context.runOnUiThread({

                                            progressDralogGenaratinglink.dismiss()

                                        })


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
                                        context.runOnUiThread({

                                            progressDralogGenaratinglink.dismiss()

                                        })
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
                                    context.runOnUiThread({

                                        progressDralogGenaratinglink.dismiss()

                                    })
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
                                    context.runOnUiThread({

                                        progressDralogGenaratinglink.dismiss()

                                    })
                                    myPhotoUrlIs = ""
                                }
                            }
                        } catch (e: java.lang.Exception) {
                            System.err.println("workkkkkkkkk 5nn errrr " + e.message)


                            System.err.println("workkkkkkkkk 5nn errrr " + e.message)

                            try {

                                try {
                                    System.err.println("workkkkkkkkk 4")



                                    downloadInstagramImageOrVideodata(
                                        URL, "", context as Activity, progressDralogGenaratinglink
                                    )


                                } catch (e: java.lang.Exception) {
                                    progressDralogGenaratinglink.dismiss()
                                    System.err.println("workkkkkkkkk 5.1")
                                    e.printStackTrace()
                                    ShowToast(context, context.getString(R.string.error_occ))

                                }

                            } catch (e: Exception) {

                                e.printStackTrace()
                                context.runOnUiThread {


                                    progressDralogGenaratinglink.dismiss()

                                    if (!context.isFinishing) {
                                        val alertDialog = AlertDialog.Builder(context).create()
                                        alertDialog.setTitle(context.getString(R.string.logininsta))
                                        alertDialog.setMessage(context.getString(R.string.urlisprivate))
                                        alertDialog.setButton(
                                            AlertDialog.BUTTON_POSITIVE,
                                            context.getString(R.string.logininsta)
                                        ) { dialog, _ ->
                                            dialog.dismiss()


//                                            val intent = Intent(
//                                                context,
//                                                InstagramLoginActivity::class.java
//                                            )
//                                            startActivityForResult(intent, 200)

                                        }

                                        alertDialog.setButton(
                                            AlertDialog.BUTTON_NEGATIVE,
                                            context.getString(R.string.cancel)
                                        ) { dialog, _ ->
                                            dialog.dismiss()


                                        }
                                        alertDialog.show()

                                    }
                                }
                            }


                        }


                    } else {

                        object : Thread() {
                            override fun run() {

                                val client = OkHttpClient().newBuilder().build()
                                val request: Request =
                                    Request.Builder().url(URL).method("GET", null)
                                        .addHeader("Cookie", iUtils.myInstagramTempCookies)
                                        .addHeader(
                                            "User-Agent", iUtils.UserAgentsList[j]
                                        ).build()
                                try {


                                    val response1: Response = client.newCall(request).execute()
                                    System.err.println("workkkkkkkkk 6 1 " + response1.code)

                                    if (response1.code == 200) {

                                        try {
                                            val listType: Type = object :
                                                TypeToken<ModelInstagramResponse?>() {}.type
                                            val modelInstagramResponse: ModelInstagramResponse =
                                                Gson().fromJson(
                                                    response1.body!!.string(), listType
                                                )


                                            if (modelInstagramResponse.modelGraphshortcode.shortcode_media.edge_sidecar_to_children != null) {
                                                val modelGetEdgetoNode: ModelGetEdgetoNode =
                                                    modelInstagramResponse.modelGraphshortcode.shortcode_media.edge_sidecar_to_children

                                                val modelEdNodeArrayList: List<ModelEdNode> =
                                                    modelGetEdgetoNode.modelEdNodes
                                                for (i in 0 until modelEdNodeArrayList.size) {
                                                    if (modelEdNodeArrayList[i].modelNode.isIs_video) {
                                                        myVideoUrlIs =
                                                            modelEdNodeArrayList[i].modelNode.video_url
                                                        DownloadFileMain.startDownloading(
                                                            context,
                                                            myVideoUrlIs,
                                                            iUtils.getVideoFilenameFromURL(
                                                                myVideoUrlIs
                                                            ),
                                                            ".mp4"
                                                        )
                                                        // etText.setText("");


                                                        context.runOnUiThread({

                                                            progressDralogGenaratinglink.dismiss()

                                                        })


                                                        myVideoUrlIs = ""
                                                    } else {
                                                        myPhotoUrlIs =
                                                            modelEdNodeArrayList[i].modelNode.display_resources[modelEdNodeArrayList[i].modelNode.display_resources.size - 1].src
                                                        DownloadFileMain.startDownloading(
                                                            context,
                                                            myPhotoUrlIs,
                                                            iUtils.getImageFilenameFromURL(
                                                                myPhotoUrlIs
                                                            ),
                                                            ".png"
                                                        )
                                                        myPhotoUrlIs = ""
                                                        context.runOnUiThread({

                                                            progressDralogGenaratinglink.dismiss()

                                                        })
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
                                                    context.runOnUiThread({

                                                        progressDralogGenaratinglink.dismiss()

                                                    })
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
                                                    context.runOnUiThread({

                                                        progressDralogGenaratinglink.dismiss()

                                                    })
                                                    myPhotoUrlIs = ""
                                                }
                                            }
                                        } catch (e: java.lang.Exception) {
                                            System.err.println("workkkkkkkkk 4vvv errrr " + e.message)


                                            e.printStackTrace()
                                            context.runOnUiThread({


                                                progressDralogGenaratinglink.dismiss()


                                            })


                                        }


                                    } else {

                                        System.err.println("workkkkkkkkk 6bbb errrr ")



                                        context.runOnUiThread {


                                            progressDralogGenaratinglink.dismiss()

                                            if (!context.isFinishing) {
                                                val alertDialog =
                                                    AlertDialog.Builder(context).create()
                                                alertDialog.setTitle(context.getString(R.string.logininsta))
                                                alertDialog.setMessage(context.getString(R.string.urlisprivate))
                                                alertDialog.setButton(
                                                    AlertDialog.BUTTON_POSITIVE,
                                                    context.getString(R.string.logininsta)
                                                ) { dialog, _ ->
                                                    dialog.dismiss()


//                                                    val intent = Intent(
//                                                        context,
//                                                        InstagramLoginActivity::class.java
//                                                    )
//                                                    startActivityForResult(intent, 200)

                                                }

                                                alertDialog.setButton(
                                                    AlertDialog.BUTTON_NEGATIVE,
                                                    context.getString(R.string.cancel)
                                                ) { dialog, _ ->
                                                    dialog.dismiss()


                                                }
                                                alertDialog.show()

                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }.start()


                    }
                    println("working errpr \t Value: " + response.body!!.string())
                } catch (e: Exception) {

                    try {
                        println("response1122334455:   " + "Failed1 " + e.message)
                        context.runOnUiThread({


                            progressDralogGenaratinglink.dismiss()

                        })


                    } catch (e: Exception) {

                    }
                }
            }
        }.start()


    }

    fun downloadInstagramImageOrVideodata(
        URL: String?,
        Coookie: String?,
        context: Activity,
        progressDralogGenaratinglink: ProgressDialog
    ) {

        val random1 = Random()
        val j = random1.nextInt(iUtils.UserAgentsList.size)
        var Cookie = Coookie
        if (TextUtils.isEmpty(Cookie)) {
            Cookie = ""
        }
        val apiService: RetrofitApiInterface =
            RetrofitClient.getClient().create(RetrofitApiInterface::class.java)


        val callResult: Call<JsonObject> = apiService.getInstagramData(
            URL, Cookie, iUtils.UserAgentsList[j]
        )
        callResult.enqueue(object : Callback<JsonObject?> {
            override fun onResponse(
                call: Call<JsonObject?>, response: retrofit2.Response<JsonObject?>
            ) {
                println("response1122334455 ress :   " + response.body())
                try {


//                                val userdata = response.body()!!.getAsJsonObject("graphql")
//                                    .getAsJsonObject("shortcode_media")
//                                binding.profileFollowersNumberTextview.setText(
//                                    userdata.getAsJsonObject(
//                                        "edge_followed_by"
//                                    )["count"].asString
//                                )
//                                binding.profileFollowingNumberTextview.setText(
//                                    userdata.getAsJsonObject(
//                                        "edge_follow"
//                                    )["count"].asString
//                                )
//                                binding.profilePostNumberTextview.setText(userdata.getAsJsonObject("edge_owner_to_timeline_media")["count"].asString)
//                                binding.profileLongIdTextview.setText(userdata["username"].asString)
//


                    val listType = object : TypeToken<ModelInstagramResponse?>() {}.type
                    val modelInstagramResponse: ModelInstagramResponse? =
                        GsonBuilder().create().fromJson<ModelInstagramResponse>(
                                response.body().toString(), listType
                            )


                    if (modelInstagramResponse != null) {
                        if (modelInstagramResponse.modelGraphshortcode.shortcode_media.edge_sidecar_to_children != null) {
                            val modelGetEdgetoNode: ModelGetEdgetoNode =
                                modelInstagramResponse.modelGraphshortcode.shortcode_media.edge_sidecar_to_children

                            val modelEdNodeArrayList: List<ModelEdNode> =
                                modelGetEdgetoNode.modelEdNodes
                            for (i in 0 until modelEdNodeArrayList.size) {
                                if (modelEdNodeArrayList[i].modelNode.isIs_video) {
                                    myVideoUrlIs = modelEdNodeArrayList[i].modelNode.video_url
                                    DownloadFileMain.startDownloading(
                                        context, myVideoUrlIs, iUtils.getVideoFilenameFromURL(
                                            myVideoUrlIs
                                        ), ".mp4"
                                    )
                                    // etText.setText("");


                                    context.runOnUiThread({

                                        progressDralogGenaratinglink.dismiss()

                                    })


                                    myVideoUrlIs = ""
                                } else {
                                    myPhotoUrlIs =
                                        modelEdNodeArrayList[i].modelNode.display_resources[modelEdNodeArrayList[i].modelNode.display_resources.size - 1].src
                                    DownloadFileMain.startDownloading(
                                        context, myPhotoUrlIs, iUtils.getImageFilenameFromURL(
                                            myPhotoUrlIs
                                        ), ".png"
                                    )
                                    myPhotoUrlIs = ""
                                    context.runOnUiThread({

                                        progressDralogGenaratinglink.dismiss()

                                    })
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
                                context.runOnUiThread({

                                    progressDralogGenaratinglink.dismiss()

                                })
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
                                context.runOnUiThread({

                                    progressDralogGenaratinglink.dismiss()

                                })
                                myPhotoUrlIs = ""
                            }
                        }
                    } else {
                        Toast.makeText(
                            context, context.getString(R.string.somthing), Toast.LENGTH_SHORT
                        ).show()

                        context.runOnUiThread({

                            progressDralogGenaratinglink.dismiss()

                        })

                    }


                } catch (e: java.lang.Exception) {


                    try {

                        try {
                            System.err.println("workkkkkkkkk 4")



                            downloadInstagramImageOrVideodata(
                                URL, "", context, progressDralogGenaratinglink
                            )


                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()

                            println("response1122334455 exe 1:   " + e.localizedMessage)

                            context.runOnUiThread({

                                progressDralogGenaratinglink.dismiss()

                            })
                            System.err.println("workkkkkkkkk 5.1")
                            e.printStackTrace()
                            ShowToast(context, context.getString(R.string.error_occ))

                        }

                    } catch (e: Exception) {

                        e.printStackTrace()


                        context.runOnUiThread {


                            progressDralogGenaratinglink.dismiss()

                            if (!context.isFinishing) {
                                e.printStackTrace()

                                Toast.makeText(
                                    context,
                                    context.getString(R.string.somthing),
                                    Toast.LENGTH_SHORT
                                ).show()
                                println("response1122334455 exe 1:   " + e.localizedMessage)

                                context.runOnUiThread({

                                    progressDralogGenaratinglink.dismiss()

                                })


                                val alertDialog = AlertDialog.Builder(context).create()
                                alertDialog.setTitle(context.getString(R.string.logininsta))
                                alertDialog.setMessage(context.getString(R.string.urlisprivate))
                                alertDialog.setButton(
                                    AlertDialog.BUTTON_POSITIVE,
                                    context.getString(R.string.logininsta)
                                ) { dialog, _ ->
                                    dialog.dismiss()


//                                    val intent = Intent(
//                                        context,
//                                        InstagramLoginActivity::class.java
//                                    )
//                                    startActivityForResult(intent, 200)

                                }

                                alertDialog.setButton(
                                    AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel)
                                ) { dialog, _ ->
                                    dialog.dismiss()


                                }
                                alertDialog.show()
                            }
                        }


                    }


                }
            }

            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                println("response1122334455:   " + "Failed0")
                context.runOnUiThread({
                    progressDralogGenaratinglink.dismiss()
                })

                Toast.makeText(
                    context, context.getString(R.string.somthing), Toast.LENGTH_SHORT
                ).show()
            }
        })


    }

    fun downloadInstagramImageOrVideodata_old_withlogin(
        URL: String?,
        Cookie: String?,
        progressDralogGenaratinglink: ProgressDialog,
        context: Activity
    ) {
        val random1 = Random()
        val j = random1.nextInt(iUtils.UserAgentsList.size)
        object : Thread() {
            override fun run() {
                Looper.prepare()
                val client = OkHttpClient().newBuilder().build()
                val request: Request =
                    Request.Builder().url(URL!!).method("GET", null).addHeader("Cookie", Cookie!!)
                        .addHeader(
                            "User-Agent", iUtils.UserAgentsList[j]
                        ).build()
                try {
                    val response = client.newCall(request).execute()



                    if (response.code == 200) {

                        val ress = response.body!!.string()
                        println("working errpr \t Value: " + ress)

                        try {
                            val listType: Type = object : TypeToken<ModelInstaWithLogin?>() {}.type
                            val modelInstagramResponse: ModelInstaWithLogin = Gson().fromJson(
                                ress, listType
                            )
                            System.out.println("workkkkk777 " + modelInstagramResponse.items.get(0).code)

                            if (modelInstagramResponse.items.get(0).mediaType == 8) {

                                val modelGetEdgetoNode = modelInstagramResponse.items.get(0)


                                val modelEdNodeArrayList: List<CarouselMedia> =
                                    modelGetEdgetoNode.carouselMedia
                                for (i in 0 until modelEdNodeArrayList.size) {
                                    if (modelEdNodeArrayList[i].mediaType == 2) {
                                        myVideoUrlIs =
                                            modelEdNodeArrayList[i].videoVersions.get(0).geturl()
                                        DownloadFileMain.startDownloading(
                                            context,
                                            myVideoUrlIs,
                                            iUtils.getVideoFilenameFromURL(myVideoUrlIs),
                                            ".mp4"
                                        )
                                        // etText.setText("");


                                        context.runOnUiThread({

                                            progressDralogGenaratinglink.dismiss()

                                        })


                                        myVideoUrlIs = ""
                                    } else {
                                        myPhotoUrlIs =
                                            modelEdNodeArrayList[i].imageVersions2.candidates.get(0)
                                                .geturl()
                                        DownloadFileMain.startDownloading(
                                            context,
                                            myPhotoUrlIs,
                                            iUtils.getVideoFilenameFromURL(myPhotoUrlIs),
                                            ".png"
                                        )
                                        myPhotoUrlIs = ""
                                        context.runOnUiThread({

                                            progressDralogGenaratinglink.dismiss()

                                        })
                                        // etText.setText("");
                                    }
                                }
                            } else {

                                val modelGetEdgetoNode = modelInstagramResponse.items.get(0)


                                if (modelGetEdgetoNode.mediaType == 2) {
                                    myVideoUrlIs = modelGetEdgetoNode.videoVersions.get(0).geturl()
                                    DownloadFileMain.startDownloading(
                                        context,
                                        myVideoUrlIs,
                                        iUtils.getVideoFilenameFromURL(myVideoUrlIs),
                                        ".mp4"
                                    )
                                    context.runOnUiThread({

                                        progressDralogGenaratinglink.dismiss()

                                    })
                                    myVideoUrlIs = ""
                                } else {
                                    myPhotoUrlIs =
                                        modelGetEdgetoNode.imageVersions2.candidates.get(0).geturl()
                                    DownloadFileMain.startDownloading(
                                        context,
                                        myPhotoUrlIs,
                                        iUtils.getVideoFilenameFromURL(myPhotoUrlIs),

                                        ".png"
                                    )
                                    context.runOnUiThread({

                                        progressDralogGenaratinglink.dismiss()

                                    })
                                    myPhotoUrlIs = ""
                                }
                            }


                        } catch (e: java.lang.Exception) {
                            System.err.println("workkkkkkkkk 5nn errrr " + e.message)

                            try {

                                try {
                                    System.err.println("workkkkkkkkk 4")


                                    val sharedPrefsFor = SharedPrefsForInstagram(context)
                                    val map =
                                        sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE)
                                    if (map != null && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != null && map.get(
                                            SharedPrefsForInstagram.PREFERENCE_USERID
                                        ) != "oopsDintWork" && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != ""
                                    ) {
                                        System.err.println("workkkkkkkkk 5.2")




                                        downloadInstagramImageOrVideodata(
                                            URL, "ds_user_id=" + map.get(
                                                SharedPrefsForInstagram.PREFERENCE_USERID
                                            ) + "; sessionid=" + map.get(
                                                SharedPrefsForInstagram.PREFERENCE_SESSIONID
                                            ), context, progressDralogGenaratinglink
                                        )


                                    } else {

                                        progressDralogGenaratinglink.dismiss()
                                        System.err.println("workkkkkkkkk 5.1")
                                        e.printStackTrace()
                                        ShowToast(context, context.getString(R.string.error_occ))
                                    }


                                } catch (e: java.lang.Exception) {
                                    progressDralogGenaratinglink.dismiss()
                                    System.err.println("workkkkkkkkk 5.1")
                                    e.printStackTrace()
                                    ShowToast(context, context.getString(R.string.error_occ))

                                }

                            } catch (e: Exception) {

                                e.printStackTrace()



                                context.runOnUiThread {

                                    progressDralogGenaratinglink.dismiss()

                                    if (!context.isFinishing) {


                                        val alertDialog = AlertDialog.Builder(context).create()
                                        alertDialog.setTitle(context.getString(R.string.logininsta))
                                        alertDialog.setMessage(context.getString(R.string.urlisprivate))
                                        alertDialog.setButton(
                                            AlertDialog.BUTTON_POSITIVE,
                                            context.getString(R.string.logininsta)
                                        ) { dialog, _ ->
                                            dialog.dismiss()


//                                            val intent = Intent(
//                                                context,
//                                                InstagramLoginActivity::class.java
//                                            )
//                                            startActivityForResult(intent, 200)

                                        }

                                        alertDialog.setButton(
                                            AlertDialog.BUTTON_NEGATIVE,
                                            context.getString(R.string.cancel)
                                        ) { dialog, _ ->
                                            dialog.dismiss()


                                        }
                                        alertDialog.show()
                                    }

                                }
                            }


                        }


                    } else {

                        object : Thread() {
                            override fun run() {

                                val client = OkHttpClient().newBuilder().build()
                                val request: Request =
                                    Request.Builder().url(URL).method("GET", null)
                                        .addHeader("Cookie", iUtils.myInstagramTempCookies)
                                        .addHeader(
                                            "User-Agent",
                                            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36"
                                        ).build()
                                try {


                                    val response1: Response = client.newCall(request).execute()

                                    if (response1.code == 200) {

                                        try {
                                            val listType: Type =
                                                object : TypeToken<ModelInstaWithLogin?>() {}.type
                                            val modelInstagramResponse: ModelInstaWithLogin =
                                                Gson().fromJson(
                                                    response.body!!.string(), listType
                                                )


                                            if (modelInstagramResponse.items.get(0).mediaType == 8) {

                                                val modelGetEdgetoNode =
                                                    modelInstagramResponse.items.get(0)


                                                val modelEdNodeArrayList: List<CarouselMedia> =
                                                    modelGetEdgetoNode.carouselMedia
                                                for (i in 0 until modelEdNodeArrayList.size) {
                                                    if (modelEdNodeArrayList[i].mediaType == 2) {
                                                        myVideoUrlIs =
                                                            modelEdNodeArrayList[i].videoVersions.get(
                                                                0
                                                            ).geturl()

                                                        DownloadFileMain.startDownloading(
                                                            context,
                                                            myVideoUrlIs,
                                                            iUtils.getVideoFilenameFromURL(
                                                                myVideoUrlIs
                                                            ),
                                                            ".mp4"
                                                        )
                                                        // etText.setText("");


                                                        context.runOnUiThread({

                                                            progressDralogGenaratinglink.dismiss()

                                                        })


                                                        myVideoUrlIs = ""
                                                    } else {
                                                        myPhotoUrlIs =
                                                            modelEdNodeArrayList[i].imageVersions2.candidates.get(
                                                                0
                                                            ).geturl()
                                                        DownloadFileMain.startDownloading(
                                                            context,
                                                            myPhotoUrlIs,
                                                            iUtils.getVideoFilenameFromURL(
                                                                myPhotoUrlIs
                                                            ),
                                                            ".png"
                                                        )
                                                        myPhotoUrlIs = ""
                                                        context.runOnUiThread({

                                                            progressDralogGenaratinglink.dismiss()

                                                        })
                                                        // etText.setText("");
                                                    }
                                                }
                                            } else {

                                                val modelGetEdgetoNode =
                                                    modelInstagramResponse.items.get(0)


                                                if (modelGetEdgetoNode.mediaType == 2) {
                                                    myVideoUrlIs =
                                                        modelGetEdgetoNode.videoVersions.get(0)
                                                            .geturl()
                                                    DownloadFileMain.startDownloading(
                                                        context,
                                                        myVideoUrlIs,
                                                        iUtils.getVideoFilenameFromURL(myVideoUrlIs),
                                                        ".mp4"
                                                    )
                                                    context.runOnUiThread({

                                                        progressDralogGenaratinglink.dismiss()

                                                    })
                                                    myVideoUrlIs = ""
                                                } else {
                                                    myPhotoUrlIs =
                                                        modelGetEdgetoNode.imageVersions2.candidates.get(
                                                            0
                                                        ).geturl()
                                                    DownloadFileMain.startDownloading(
                                                        context,
                                                        myPhotoUrlIs,
                                                        iUtils.getVideoFilenameFromURL(myPhotoUrlIs),
                                                        ".png"
                                                    )
                                                    context.runOnUiThread({

                                                        progressDralogGenaratinglink.dismiss()

                                                    })
                                                    myPhotoUrlIs = ""
                                                }
                                            }


                                        } catch (e: java.lang.Exception) {
                                            System.err.println("workkkkkkkkk 5nn errrr " + e.message)


                                            e.printStackTrace()
                                            context.runOnUiThread {


                                                progressDralogGenaratinglink.dismiss()

                                                if (!context.isFinishing) {
                                                    val alertDialog =
                                                        AlertDialog.Builder(context).create()
                                                    alertDialog.setTitle(context.getString(R.string.logininsta))
                                                    alertDialog.setMessage(context.getString(R.string.urlisprivate))
                                                    alertDialog.setButton(
                                                        AlertDialog.BUTTON_POSITIVE,
                                                        context.getString(R.string.logininsta)
                                                    ) { dialog, _ ->
                                                        dialog.dismiss()


//                                                        val intent = Intent(
//                                                            context,
//                                                            InstagramLoginActivity::class.java
//                                                        )
//                                                        startActivityForResult(intent, 200)

                                                    }

                                                    alertDialog.setButton(
                                                        AlertDialog.BUTTON_NEGATIVE,
                                                        context.getString(R.string.cancel)
                                                    ) { dialog, _ ->
                                                        dialog.dismiss()


                                                    }
                                                    alertDialog.show()

                                                }
                                            }

                                        }


                                    } else {

                                        System.err.println("workkkkkkkkk 6bbb errrr ")


                                        context.runOnUiThread {


                                            progressDralogGenaratinglink.dismiss()

                                            if (!context.isFinishing) {
                                                val alertDialog =
                                                    AlertDialog.Builder(context).create()
                                                alertDialog.setTitle(context.getString(R.string.logininsta))
                                                alertDialog.setMessage(context.getString(R.string.urlisprivate))
                                                alertDialog.setButton(
                                                    AlertDialog.BUTTON_POSITIVE,
                                                    context.getString(R.string.logininsta)
                                                ) { dialog, _ ->
                                                    dialog.dismiss()


//                                                    val intent = Intent(
//                                                        context,
//                                                        InstagramLoginActivity::class.java
//                                                    )
//                                                    startActivityForResult(intent, 200)

                                                }

                                                alertDialog.setButton(
                                                    AlertDialog.BUTTON_NEGATIVE,
                                                    context.getString(R.string.cancel)
                                                ) { dialog, _ ->
                                                    dialog.dismiss()


                                                }
                                                alertDialog.show()

                                            }
                                        }

                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }.start()


                    }
                } catch (e: Exception) {

                    try {
                        println("response1122334455:   " + "Failed1 " + e.message)
                        context.runOnUiThread({


                            progressDralogGenaratinglink.dismiss()

                        })


                    } catch (e: Exception) {

                    }
                }
            }
        }.start()


    }

    fun downloadInstagramImageOrVideodata_withlogin(
        URL: String?,
        Cookie: String?,
        context: Activity,
        progressDralogGenaratinglink: ProgressDialog
    ) {
/*instagram product types
* product_type
*
* igtv "media_type": 2
* carousel_container  "media_type": 8
* clips  "media_type": 2
* feed   "media_type": 1
* */

        val random1 = Random()
        val j = random1.nextInt(iUtils.UserAgentsList.size)

        var Cookie = Cookie
        if (TextUtils.isEmpty(Cookie)) {
            Cookie = ""
        }
        val apiService: RetrofitApiInterface =
            RetrofitClient.getClient().create(RetrofitApiInterface::class.java)


        val callResult: Call<JsonObject> = apiService.getInstagramData(
            URL, Cookie, iUtils.UserAgentsList[j]
        )
        callResult.enqueue(object : Callback<JsonObject?> {
            override fun onResponse(
                call: Call<JsonObject?>, response: retrofit2.Response<JsonObject?>
            ) {

                try {
                    val listType: Type = object : TypeToken<ModelInstaWithLogin?>() {}.type
                    val modelInstagramResponse: ModelInstaWithLogin = Gson().fromJson(
                        response.body(), listType
                    )
                    System.out.println("workkkkk777 " + modelInstagramResponse.items.get(0).code)

                    if (modelInstagramResponse.items.get(0).mediaType == 8) {

                        val modelGetEdgetoNode = modelInstagramResponse.items.get(0)


                        val modelEdNodeArrayList: List<CarouselMedia> =
                            modelGetEdgetoNode.carouselMedia
                        for (i in 0 until modelEdNodeArrayList.size) {
                            if (modelEdNodeArrayList[i].mediaType == 2) {
                                myVideoUrlIs = modelEdNodeArrayList[i].videoVersions.get(0).geturl()
                                DownloadFileMain.startDownloading(
                                    context,
                                    myVideoUrlIs,
                                    iUtils.getVideoFilenameFromURL(myVideoUrlIs),
                                    ".mp4"
                                )
                                // etText.setText("");


                                context.runOnUiThread({

                                    progressDralogGenaratinglink.dismiss()

                                })


                                myVideoUrlIs = ""
                            } else {
                                myPhotoUrlIs =
                                    modelEdNodeArrayList[i].imageVersions2.candidates.get(0)
                                        .geturl()
                                DownloadFileMain.startDownloading(
                                    context,
                                    myPhotoUrlIs,
                                    iUtils.getVideoFilenameFromURL(myPhotoUrlIs),
                                    ".png"
                                )
                                myPhotoUrlIs = ""
                                context.runOnUiThread({

                                    progressDralogGenaratinglink.dismiss()

                                })
                                // etText.setText("");
                            }
                        }
                    } else {

                        val modelGetEdgetoNode = modelInstagramResponse.items.get(0)


                        if (modelGetEdgetoNode.mediaType == 2) {
                            myVideoUrlIs = modelGetEdgetoNode.videoVersions.get(0).geturl()
                            DownloadFileMain.startDownloading(
                                context,
                                myVideoUrlIs,
                                iUtils.getVideoFilenameFromURL(myVideoUrlIs),
                                ".mp4"
                            )
                            context.runOnUiThread({

                                progressDralogGenaratinglink.dismiss()

                            })
                            myVideoUrlIs = ""
                        } else {
                            myPhotoUrlIs =
                                modelGetEdgetoNode.imageVersions2.candidates.get(0).geturl()
                            DownloadFileMain.startDownloading(
                                context,
                                myPhotoUrlIs,
                                iUtils.getVideoFilenameFromURL(myPhotoUrlIs),
                                ".png"
                            )
                            context.runOnUiThread({

                                progressDralogGenaratinglink.dismiss()

                            })
                            myPhotoUrlIs = ""
                        }
                    }


                } catch (e: java.lang.Exception) {
                    System.err.println("workkkkkkkkk 5nn errrr " + e.message)


                    try {

                        try {
                            System.err.println("workkkkkkkkk 4")


                            val sharedPrefsFor = SharedPrefsForInstagram(context)
                            val map =
                                sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE)
                            if (map != null && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != null && map.get(
                                    SharedPrefsForInstagram.PREFERENCE_USERID
                                ) != "oopsDintWork" && map.get(SharedPrefsForInstagram.PREFERENCE_USERID) != ""
                            ) {
                                System.err.println("workkkkkkkkk 5.2")




                                downloadInstagramImageOrVideodata_old(
                                    URL,
                                    "ds_user_id=" + map.get(
                                        SharedPrefsForInstagram.PREFERENCE_USERID
                                    ) + "; sessionid=" + map.get(SharedPrefsForInstagram.PREFERENCE_SESSIONID),
                                    context,
                                    progressDralogGenaratinglink

                                )


                            } else {

                                progressDralogGenaratinglink.dismiss()
                                System.err.println("workkkkkkkkk 5.1")
                                e.printStackTrace()
                                ShowToast(context, context.getString(R.string.error_occ))
                            }


                        } catch (e: java.lang.Exception) {
                            progressDralogGenaratinglink.dismiss()
                            System.err.println("workkkkkkkkk 5.1")
                            e.printStackTrace()
                            ShowToast(context, context.getString(R.string.error_occ))

                        }

                    } catch (e: Exception) {

                        e.printStackTrace()
                        context.runOnUiThread {


                            progressDralogGenaratinglink.dismiss()

                            if (!context.isFinishing) {
                                val alertDialog = AlertDialog.Builder(context).create()
                                alertDialog.setTitle(context.getString(R.string.logininsta))
                                alertDialog.setMessage(context.getString(R.string.urlisprivate))
                                alertDialog.setButton(
                                    AlertDialog.BUTTON_POSITIVE,
                                    context.getString(R.string.logininsta)
                                ) { dialog, _ ->
                                    dialog.dismiss()


//                                    val intent = Intent(
//                                        context,
//                                        InstagramLoginActivity::class.java
//                                    )
//                                    startActivityForResult(intent, 200)

                                }

                                alertDialog.setButton(
                                    AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel)
                                ) { dialog, _ ->
                                    dialog.dismiss()


                                }
                                alertDialog.show()

                            }
                        }
                    }


                }


            }

            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                println("response1122334455:   " + "Failed0")
                context.runOnUiThread {
                    progressDralogGenaratinglink.dismiss()
                }

                Toast.makeText(
                    context, context.getString(R.string.somthing), Toast.LENGTH_SHORT
                ).show()
            }
        })


    }


}