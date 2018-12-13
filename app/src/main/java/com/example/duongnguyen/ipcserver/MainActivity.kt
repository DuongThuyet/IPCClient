package com.example.duongnguyen.ipcserver

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.DeadObjectException
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val ACTION = "com.example.duongnguyen.service.MusicService.BIND"
    val PACKAGE = "com.example.duongnguyen.ipcserver"

    private lateinit var mService: IMusicServiceServer
    private var mIsServiceConnected: Boolean = false

    private var mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            try {
                mService = IMusicServiceServer.Stub.asInterface(service)
                mIsServiceConnected = true
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mIsServiceConnected = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initService()
        tv.setOnClickListener {
            startMusic()
        }
        tv1.setOnClickListener {
            stopMusic()
        }

    }

    private fun initService() {
        val intent = Intent(ACTION)
        intent.setPackage(PACKAGE)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun startMusic() {
        if (!mIsServiceConnected) {
            return
        }
        try {
            mService.play()
            Toast.makeText(this, mService.currentDuration.toString(), Toast.LENGTH_SHORT).show()
        } catch (e: DeadObjectException) {
            e.printStackTrace()
        }


    }

    private fun stopMusic() {
        if (!mIsServiceConnected) {
            return
        }
        try {
            mService.pause()
            Toast.makeText(this, mService.songName, Toast.LENGTH_SHORT).show()
        } catch (e: DeadObjectException) {
            e.printStackTrace()
        }


    }

}
