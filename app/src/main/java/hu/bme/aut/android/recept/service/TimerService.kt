package hu.bme.aut.android.recept.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import hu.bme.aut.android.recept.MainActivity
import hu.bme.aut.android.recept.R

class TimerService : Service() {


    private var timer: CountDownTimer? = null
    private val CHANNEL_ID = "TimerChannel"
    private val NOTIFICATION_ID = 1

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        if (action == "STOP") {
            stopTimer()
        } else {
            //get time from intent if no time defualt 5min = 300000l
            val duration = intent?.getLongExtra("DURATION", 5 * 60 * 1000L) ?: 300000L
            startTimer(duration)
        }
        return START_NOT_STICKY
    }

    private fun startTimer(duration: Long) {
        createNotificationChannel()

        val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val stopIntent = Intent(this, TimerService::class.java).apply { action = "STOP" }
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Cooking Timer ")
            .setContentText("Starting...")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Stop", stopPendingIntent)
            .setOngoing(true)
            .setOnlyAlertOnce(true)


        startForeground(NOTIFICATION_ID, notificationBuilder.build())

        //cancel previous one
        timer?.cancel()
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val sec = (millisUntilFinished / 1000) % 60
                val min = (millisUntilFinished / 1000) / 60

                notificationBuilder.setContentText("Remaining: ${min}m ${sec}s")
                val manager = getSystemService(NotificationManager::class.java)
                manager.notify(NOTIFICATION_ID, notificationBuilder.build())
            }

            override fun onFinish() {

                notificationBuilder.setContentText("Time's up! Food is ready!")
                    .setOngoing(false).clearActions()
                val manager = getSystemService(NotificationManager::class.java)
                manager.notify(NOTIFICATION_ID, notificationBuilder.build())
                stopForeground(STOP_FOREGROUND_DETACH)
                stopSelf()
            }
        }.start()
    }

    private fun stopTimer() {
        timer?.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, "Cooking Timer", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }
}