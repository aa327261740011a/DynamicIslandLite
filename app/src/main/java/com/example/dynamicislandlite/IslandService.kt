package com.example.dynamicislandlite

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView

class IslandService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var islandView: View
    private lateinit var islandText: TextView

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        islandView = LayoutInflater.from(this)
            .inflate(R.layout.island_view, null)

        islandText = islandView.findViewById(R.id.islandText)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params.y = 80

        windowManager.addView(islandView, params)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val title = intent?.getStringExtra("title") ?: "Music Playing"
        val text = intent?.getStringExtra("text") ?: ""
        islandText.text = "$title  $text"
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::islandView.isInitialized) {
            windowManager.removeView(islandView)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
