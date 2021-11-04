package test.zadanie.com

import android.Manifest
import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class URLActivity : AppCompatActivity() {


    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0
    lateinit var web:WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u_r_l)

        val func = Func()
        web = findViewById(R.id.web)
        val pref = Prefs(applicationContext)


        val webSettings: WebSettings = web.settings
        webSettings.javaScriptEnabled = true
        web.webViewClient = WebViewClient()
        CookieManager.getInstance().setAcceptThirdPartyCookies(web, true);
        web.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            val request = DownloadManager.Request(
                Uri.parse(url)
            )
            request.setMimeType(mimeType)
            val cookies = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("cookie", cookies)
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Downloading File...")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                    url, contentDisposition, mimeType
                )
            )
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()
        })

        if(func.checkForInternet(this)){
            web.loadUrl(pref.getString("ActiveURL"))
        }
        else{
            func.onAlertDialog(View(this))
        }
    }

    override fun onBackPressed() {

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            val func = Func()

            Prefs(applicationContext).setString("ActiveURL", web.url.toString())
            func.onExitDialog(View(this))
        }
        else {
            if (web.canGoBack()) {
                web.goBack()
            }
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();

    }
}