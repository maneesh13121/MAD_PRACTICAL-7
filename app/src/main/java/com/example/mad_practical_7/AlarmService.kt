package com.example.mad_practical_7

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AlarmService : Service() {

    var mp: MediaPlayer? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent != null) {
            mp = MediaPlayer.create(this,R.raw.alarm);
            mp?.start()
        }
        return START_STICKY

    }

    override fun onDestroy() {
        mp?.stop()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Not yet implemented")
    }
}