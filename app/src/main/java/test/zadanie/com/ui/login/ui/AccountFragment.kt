package test.zadanie.com.ui.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import test.zadanie.com.Prefs
import test.zadanie.com.R
import test.zadanie.com.ui.login.LoginFragment

class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pref = Prefs(requireContext())

        var exit: Button = view?.findViewById(R.id.exitBtn)!!
        val logText:TextView = view?.findViewById(R.id.wlc)!!

        logText.setText("Welcome, " + pref.getString("login"))

        exit.setOnClickListener(View.OnClickListener {

            pref.setBoolean("loginState", false)

            var fr = fragmentManager?.beginTransaction()
            fr?.replace(R.id.fragment_container, LoginFragment())
            fr?.commit()
        })
    }

}