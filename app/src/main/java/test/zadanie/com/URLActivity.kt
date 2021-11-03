package test.zadanie.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView

class URLActivity : AppCompatActivity() {

    lateinit var web:WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u_r_l)

        val func = Func()
        web = findViewById(R.id.web)
        val pref = Prefs(applicationContext)

        if(func.checkForInternet(this)){
            func.configureWebView(web)
            web.loadUrl(pref.getString("ActiveURL"))
        }
        else{
            func.onAlertDialog(View(this))
        }
    }

    override fun onBackPressed() {
        val pref = Prefs(applicationContext)
        pref.setString("ActiveURL", web.url.toString())
        val func = Func()
        func.onExitDialog(View(this))
    }
}