package test.zadanie.com.ui.login.ui.reg

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

class RegFragment : Fragment() {

    companion object {
        fun newInstance() = RegFragment()
    }

    private lateinit var viewModel: RegViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.reg_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegViewModel::class.java)

        var reg: Button = view?.findViewById(R.id.regBtn)!!
        reg.setOnClickListener(View.OnClickListener {
            val pass: EditText = view?.findViewById(R.id.pas)!!
            val log: EditText = view?.findViewById(R.id.log)!!
            val pref = Prefs(requireContext())
            pref.setString("login", log.text.toString())
            pref.setString("password", pass.text.toString())
            Toast.makeText(this.context,"Success!", Toast.LENGTH_LONG).show()
            var fr = fragmentManager?.beginTransaction()
            fr?.replace(R.id.fragment_container,LoginFragment())
            fr?.commit()
        })

        var back: Button = view?.findViewById(R.id.backBtn)!!
        back.setOnClickListener(View.OnClickListener {
            var fr = fragmentManager?.beginTransaction()
            fr?.replace(R.id.fragment_container,LoginFragment())
            fr?.commit()
        })
    }

}