package test.zadanie.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import test.zadanie.com.ui.login.LoginFragment

class NoURLActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_u_r_l)

        val func = Func()

        if(func.checkForInternet(this)){
            val textFragment = LoginFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()
            Log.i("LoginState", Prefs(applicationContext).getBoolean("LoginState").toString())
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