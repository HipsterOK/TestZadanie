package test.zadanie.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView

class URLActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u_r_l)

        val func = Func()
        val web:WebView = findViewById(R.id.web)
        val pref = Prefs(applicationContext)

        if(func.checkForInternet(this)){
            func.configureWebView(web)
            web.loadUrl(pref.getString("URL"))
        }
        else{
            func.onAlertDialog(View(this))
        }
    }

    override fun onBackPressed() {
        val func = Func()

        func.onExitDialog(View(this))
    }
}