package com.rajkumarrajan.latesttechexample.Utils

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rajkumarrajan.latesttechexample.R

class SessionManager(var mContext: Context) {
    lateinit var dialog: Dialog
    fun displayLoader() {
        val builder = AlertDialog.Builder(mContext)
        builder.setView(R.layout.loader_progress)
        dialog = builder.create()
        dialog.show()
    }

    fun dismissLoader() {
        dialog.dismiss()
    }

    fun shortToast(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
            .show()
    }

    fun longToast(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG)
            .show()
    }

    fun checkInternetStatus(): Boolean {
        val connectivity =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        for (i in info.indices)
            if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }

        return false
    }

}