package test.zadanie.com.ui.login.ui.recoverypass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import test.zadanie.com.Prefs
import test.zadanie.com.R
import test.zadanie.com.ui.login.LoginFragment

class RecoveryPassFragment : Fragment() {

    companion object {
        fun newInstance() = RecoveryPassFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.recovery_pass_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var reg: Button = view?.findViewById(R.id.recBtn)!!
        reg.setOnClickListener(View.OnClickListener {
            val log: EditText = view?.findViewById(R.id.log)!!
            val pref = Prefs(requireContext())

            if(log.text.toString() == pref.getString("login")) {
                Toast.makeText(this.context, "Your password: " + pref.getString("password"), Toast.LENGTH_LONG).show()
                var fr = fragmentManager?.beginTransaction()
                fr?.replace(R.id.fragment_container, LoginFragment())
                fr?.commit()
            } else{
                Toast.makeText(this.context, "Account not found!", Toast.LENGTH_LONG).show()
            }
        })

        var back: Button = view?.findViewById(R.id.backBtn)!!
        back.setOnClickListener(View.OnClickListener {
            var fr = fragmentManager?.beginTransaction()
            fr?.replace(R.id.fragment_container, LoginFragment())
            fr?.commit()
        })
    }

}