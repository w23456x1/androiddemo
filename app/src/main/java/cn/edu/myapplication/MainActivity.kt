package cn.edu.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val Channel_ID = "my_channel"
    val Notification_ID = 1

    lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openSqLiteHelper = MyOpenSqLiteHelper(this, 1)
        db = openSqLiteHelper.writableDatabase

        add.setOnClickListener {
            val x = one_number.text.toString()
            val y = two_number.text.toString()
            val num1 = x.toInt()
            val num2 = y.toInt()
            val result = num1 + num2
            textView5.text = result.toString()

            val contentValues = ContentValues().apply {
                put("num1", num1)
                put("num2", num2)
                put("result", result)
            }
            db.insert(TABLE_NAME, null, contentValues)
            Log.d("Test","$contentValues")

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder: Notification.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel =
                    NotificationChannel(Channel_ID, "Add", NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(notificationChannel)
                builder = Notification.Builder(this, Channel_ID)
            } else {
                builder = Notification.Builder(this)
            }

            val notification = builder.setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Add Notification")
                .setContentText("计算结果为" + result.toString())
                .build()

            notificationManager.notify(Notification_ID, notification)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this,HistoryActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

}