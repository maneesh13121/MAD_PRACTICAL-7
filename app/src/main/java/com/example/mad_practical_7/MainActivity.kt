package com.example.mad_practical_7

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mad_practical_7.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.cardListAlarm.visibility = View.GONE
        binding.btnCreateAlarm.setOnClickListener {
            showTimerDialog()
        }
        binding.btnCancelAlarm.setOnClickListener {
            setAlarm(-1,"Stop")
            binding.cardListAlarm.visibility = View.GONE
        }
    }

    private fun showTimerDialog() {
        val picker = TimePickerDialog(
            this, {tp, sHour, sMinute -> sendDialogDataToActivity(sHour, sMinute) },
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            false
        )
        picker.show()
    }

    private fun sendDialogDataToActivity(hour: Int, minute: Int) {
        val alarmCalendar = Calendar.getInstance()
        val year: Int = alarmCalendar.get(Calendar.YEAR)
        val month: Int = alarmCalendar.get(Calendar.MONTH)
        val day: Int = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year, month, day, hour, minute, 0)
        binding.textAlarmTime.text = SimpleDateFormat("hh:mm ss a").format(alarmCalendar.time)
        binding.cardListAlarm.visibility = View.VISIBLE
        setAlarm(alarmCalendar.timeInMillis, "Start")
    }

    private fun setAlarm(millisTime: Long, str: String) {
        val intent = Intent(this,AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service",str)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext,234324243,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if (str == "Start") {
            alarmManager.setExact{
                AlarmManager.RTC_WAKEUP
                millisTime
                pendingIntent
            }
            Toast.makeText(this,"Start Alarm",Toast.LENGTH_SHORT).show()
        }else if (str == "Stop")
        {
            alarmManager.cancel(pendingIntent)
            sendBroadcast(intent)
            Toast.makeText(this,"Stop Alarm",Toast.LENGTH_SHORT).show()
        }

    }

}