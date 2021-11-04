package test.zadanie.com

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*
import android.Manifest;


class SplashScreenActivity : AppCompatActivity() {


    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val pref = Prefs(applicationContext)
        val gson = Gson()
        val func = Func()

        AppMetrica()
        OneSignal()

        Handler().postDelayed({
            Thread {
                if (func.checkForInternet(this)) {

                    Log.i("fRun", pref.getBoolean("FirstRun").toString())
                    if (pref.getBoolean("firstRun")) {

                        val result = func.doRequest()
                        val result1 = result.component1()
                        Log.i("Result", result1.toString())
                        val jsonObject = JSONTokener(result1.toString()).nextValue() as JSONObject
                        val url = jsonObject.getString("url")
                        Log.i("Url: ", url)
                        pref.setString("URL", url)
                        pref.setString("ActiveURL", url)
                        pref.setBoolean("LoginState", false)


                        pref.setBoolean("firstRun", false)
                    }

                    Log.i("Url", pref.getString("URL"))
                    Log.i("ActiveUrl", pref.getString("URL"))


                    if (pref.getString("URL") != "null") {
                        startActivity(Intent(this, URLActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this, NoURLActivity::class.java))
                        finish()
                    }

                } else {
                    func.onAlertDialog(View(this))
                }
            }.start()
        }, SPLASH_TIME_OUT)

    }
}