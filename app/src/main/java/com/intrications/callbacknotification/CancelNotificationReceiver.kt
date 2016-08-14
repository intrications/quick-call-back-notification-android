package com.intrications.callbacknotification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CancelNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context, intent: Intent) {
        val number = intent.getStringExtra("phone_number")
        val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(number.hashCode())
    }
}
