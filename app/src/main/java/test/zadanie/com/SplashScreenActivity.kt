package test.zadanie.com

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import org.json.JSONObject
import org.json.JSONTokener


class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000// 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        val pref = Prefs(applicationContext)
        val func = Func()

        AppMetrica()
        OneSignal()

        if (!func.checkPermission(applicationContext)) {
            requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE), 200)
        }

        Handler().postDelayed({
            run {

                    if (func.checkForInternet(this)) {

                        Log.i("fRun", pref.getBoolean("FirstRun").toString())
                        if (pref.getBoolean("firstRun")) {

                            try {
                                val result = func.doRequest()
                                Log.i("Result", result.toString())
                                val result1 = result.component1()
                                Log.i("Result", result1.toString())
                                val jsonObject =
                                    JSONTokener(result1.toString()).nextValue() as JSONObject
                                val url = jsonObject.getString("url")
                                Log.i("Url: ", url)
                                pref.setString("URL", url)
                                pref.setString("ActiveURL", url)
                            } catch (e: Exception) {
                                Log.i("InterEx", e.toString())
                            }
                            pref.setBoolean("firstRun", false)
                            pref.setBoolean("loginState", false)
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
            }
        }, SPLASH_TIME_OUT)
    }


}