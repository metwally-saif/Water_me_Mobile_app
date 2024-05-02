package com.example.mobileapp

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.Context
import android.database.Cursor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder
import com.example.mobileapp.PlantDatabaseHelper.COLUMN_ID
import com.example.mobileapp.PlantDatabaseHelper.COLUMN_LAST_WATERED
import com.example.mobileapp.PlantDatabaseHelper.COLUMN_INTERVAL
import com.example.mobileapp.PlantDatabaseHelper.COLUMN_NAME

class LastWatered_timings : Service() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val dbHelper = PlantDatabaseHelper(this)
        val readble = dbHelper.readableDatabase
        val plants = mutableListOf<ConcretePlant>()

        val db : SQLiteDatabase = dbHelper.readableDatabase;
        val projection = arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_INTERVAL, COLUMN_LAST_WATERED)
        val cursor : Cursor = db.query(
            PlantDatabaseHelper.TABLE_PLANTS,
            projection,
            null,
            null,
            null,
            null,
            null
        );

        while (cursor.moveToNext()) {
            val plantName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val plantInterval = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INTERVAL))
            val lastWatered = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_WATERED))
            plants.add(ConcretePlant(plantName, plantInterval, lastWatered))
        }
        cursor.close()

        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        val currentDate = sdf.format(Date())
        for (plant in plants) {
            val lastWatered = plant.LastWatered()
            val plantName = plant.PlantName()
            val plantInterval = plant.Interval()
            val lastWateredDate = sdf.parse(lastWatered)
            val currentDate = sdf.parse(currentDate)
            val diff = currentDate.time - lastWateredDate.time
            val diffDays = diff / (24 * 60 * 60 * 1000)
            if (diffDays >= 1) {
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channelId = "lastWatered"
                    val channel = NotificationChannel(channelId, "Last Watered", NotificationManager.IMPORTANCE_HIGH)
                    notificationManager.createNotificationChannel(channel)
                    val notification = NotificationCompat.Builder(this, channelId)
                        .setContentTitle("Last Watered")
                        .setContentText("$plantName needs to be watered")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_REMINDER)
                        .build()
                    notificationManager.notify(1, notification)
                } else {
                    val notification = NotificationCompat.Builder(this)
                        .setContentTitle("Last Watered")
                        .setContentText("$plantName needs to be watered")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_REMINDER)
                        .build()
                    notificationManager.notify(1, notification)
                }
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}