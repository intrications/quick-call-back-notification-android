package com.intrications.callbacknotification

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.NotificationCompat
import com.intrications.callbacknotification.util.PhonecallReceiver
import java.util.*

class CallReceiver : PhonecallReceiver() {

    override fun onIncomingCallStarted(ctx: Context, number: String, start: Date) {

        showNotification(ctx, number)
        setAlarmToHideNotification(ctx, number)
    }

    private fun setAlarmToHideNotification(ctx: Context, number: String) {
        val intent = Intent(ctx, CancelNotificationReceiver::class.java)
        intent.putExtra("phone_number", number)
        val pendingIntent = PendingIntent.getBroadcast(ctx, 999, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val tenMinutes = 1000 * 60 * 10
        am.set(AlarmManager.RTC, System.currentTimeMillis() + tenMinutes, pendingIntent)
    }

    private fun showNotification(ctx: Context, number: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:" + number)
        val pendingIntent = PendingIntent.getActivity(ctx, 0, callIntent, 0)

        val builder = NotificationCompat.Builder(ctx)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_status)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle("Phone call from " + number)
                .setContentText("Tap to call back")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Use hashcode of number so there is one notification for each phone number
        nm.notify(number.hashCode(), builder.build())
    }

    override fun onOutgoingCallStarted(ctx: Context, number: String, start: Date) {
    }

    override fun onIncomingCallEnded(ctx: Context, number: String, start: Date, end: Date) {
    }

    override fun onOutgoingCallEnded(ctx: Context, number: String, start: Date, end: Date) {
    }

    override fun onMissedCall(ctx: Context, number: String, start: Date) {
    }

}