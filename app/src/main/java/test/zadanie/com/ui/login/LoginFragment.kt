package test.zadanie.com.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import test.zadanie.com.Prefs
import test.zadanie.com.R
import test.zadanie.com.ui.login.ui.AccountFragment
import test.zadanie.com.ui.login.ui.recoverypass.RecoveryPassFragment
import test.zadanie.com.ui.login.ui.reg.RegFragment

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pref = Prefs(requireContext())

        if(pref.getBoolean("loginState")){
            var fr = fragmentManager?.beginTransaction()
            fr?.replace(R.id.fragment_container, AccountFragment())
            fr?.commit()
        }
        else {
            var login: Button = view?.findViewById(R.id.loginBtn)!!
            login.setOnClickListener(View.OnClickListener {
                val pass: EditText = view?.findViewById(R.id.pas)!!
                val log: EditText = view?.findViewById(R.id.log)!!
                if (pref.getString("login") == log.text.toString() && pref.getString("password") == pass.text.toString()) {
                    pref.setBoolean("loginState", true)
                    var fr = fragmentManager?.beginTransaction()
                    fr?.replace(R.id.fragment_container, AccountFragment())
                    fr?.commit()
                } else {
                    Toast.makeText(this.context, "Account not found!", Toast.LENGTH_LONG).show()
                }
            })
        }

        var reg:Button = view?.findViewById(R.id.regBtn)!!
        reg.setOnClickListener(View.OnClickListener {
            var fr = fragmentManager?.beginTransaction()
            fr?.replace(R.id.fragment_container,RegFragment())
            fr?.commit()
        })

        var forPass:Button = view?.findViewById(R.id.forPassBtn)!!
        forPass.setOnClickListener(View.OnClickListener {
            var fr = fragmentManager?.beginTransaction()
            fr?.replace(R.id.fragment_container, RecoveryPassFragment())
            fr?.commit()
        })
    }
}