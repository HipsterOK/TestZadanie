package test.zadanie.com

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class Func : AppCompatActivity() {


    fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun onAlertDialog(view: View) {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Error")
        builder.setMessage("Please, check your Internet connection and restart app.")
        builder.setPositiveButton(
                "OK") { _, _ ->
            exitProcess(0);
        }

        builder.show()
    }

    fun onExitDialog(view: View){
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Exit")
        builder.setMessage("Do you really want to exit?")
        builder.setPositiveButton(
                "Yes") { _, _ ->
            exitProcess(0);
        }
        builder.setNegativeButton(
                "No"){ _, _ -> }

        builder.show()
    }

    fun configureWebView(webView: WebView?) {
//        webView?.settings?.userAgentString = getUserAgent()
        webView?.settings?.cacheMode = WebSettings.LOAD_DEFAULT
        webView?.settings?.databaseEnabled = true
        webView?.settings?.domStorageEnabled = true
        webView?.settings?.javaScriptEnabled = true
        webView?.settings?.mediaPlaybackRequiresUserGesture = false
        webView?.settings?.allowFileAccessFromFileURLs = true
        webView?.settings?.allowUniversalAccessFromFileURLs = true
        webView?.webChromeClient = WebChromeClient()
        webView?.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView?.isLongClickable = false
        webView?.setOnLongClickListener {
            true
        }
        webView?.settings?.setSupportMultipleWindows(true)
    }
}