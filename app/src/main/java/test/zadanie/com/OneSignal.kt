package test.zadanie.com

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onesignal.OneSignal


class OneSignal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // OneSignal Initialization
        OneSignal.initWithContext(this)

        // on below line we are setting
        // app id for our one signal
        OneSignal.setAppId("399e4775-3b2c-44f5-965b-bc23963a9451")
    }
}