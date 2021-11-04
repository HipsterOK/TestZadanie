package test.zadanie.com

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess


class Func : AppCompatActivity() {

    fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun onAlertDialog(view: View) {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Error")
        builder.setMessage("Please, check your Internet connection and restart app.")
        builder.setPositiveButton(
            "OK"
        ) { _, _ ->
            exitProcess(0)
        }

        builder.show()
    }

    fun onExitDialog(view: View){
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Exit")
        builder.setMessage("Do you really want to exit?")
        builder.setPositiveButton(
            "Yes"
        ) { _, _ ->
            exitProcess(0)
        }
        builder.setNegativeButton(
            "No"
        ){ _, _ -> }

        builder.show()
    }

    fun doRequest() = runBlocking {
        val (_, _, result) = Fuel.get("https://sporter1.ru/aka.php?id=2gy3oyj4vsvzmo484hxp")
            .responseString()
        result
    }

    fun checkPermission(context: Context): Boolean {
        val writePerm = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val intPerm = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
        val accesPerm = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)
        return writePerm == PackageManager.PERMISSION_GRANTED && intPerm == PackageManager.PERMISSION_GRANTED && accesPerm == PackageManager.PERMISSION_GRANTED
    }
}