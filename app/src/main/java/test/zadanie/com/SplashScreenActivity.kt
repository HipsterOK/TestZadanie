package test.zadanie.com

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import org.json.JSONTokener


class SplashScreenActivity : AppCompatActivity() {

    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions

    val list = listOf<String>(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private val SPLASH_TIME_OUT:Long = 3000// 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        val pref = Prefs(applicationContext)
        val func = Func()

        AppMetrica()
        OneSignal()

        Handler().postDelayed({
            run {

                if (func.checkForInternet(this)) {

                    Log.i("fRun", pref.getBoolean("FirstRun").toString())
                    if (pref.getBoolean("firstRun")) {

                        managePermissions = ManagePermissions(this, list, PermissionsRequestCode)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            managePermissions.checkPermissions()
                        }

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
                            pref.setBoolean("firstRun", false)
                        } catch (e: Exception) {
                            Log.i("InterEx", e.toString())
                        }
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