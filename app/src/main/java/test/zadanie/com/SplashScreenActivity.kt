package test.zadanie.com

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.join
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class SplashScreenActivity : AppCompatActivity() {


    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val pref = Prefs(applicationContext)
        val gson = Gson()
        val func = Func()


        Handler().postDelayed({
            Thread {
            if (func.checkForInternet(this)) {

                Log.i("fRun", pref.getBoolean("FirstRun").toString())
                if (pref.getBoolean("firstRun")) {

                        val result = func.doRequest()
                        val result1=result.component1()
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