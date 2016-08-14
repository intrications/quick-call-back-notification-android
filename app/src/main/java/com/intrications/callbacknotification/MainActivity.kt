package com.intrications.callbacknotification

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission.setOnClickListener { requestPermission() }
    }

    override fun onResume() {
        super.onResume()

        checkForPermission()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                MY_PERMISSIONS_REQUEST_READ_PHONE_STATE)
    }

    private fun checkForPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            text.setText(R.string.permission_explanation)
        } else {
            showPermissionGrantedText()
        }
    }

    private fun showPermissionGrantedText() {
        text.setText(R.string.app_explanation)
        requestPermission.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_PHONE_STATE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPermissionGrantedText()
                }
                return
            }
        }
    }

    companion object {

        private val MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 8888
    }
}
