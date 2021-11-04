package test.zadanie.com

import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
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

        if(func.checkForInternet(this)){
//            func.configureWebView(web)
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

            Prefs(applicationContext).setString("ActiveURL",web.url.toString())
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