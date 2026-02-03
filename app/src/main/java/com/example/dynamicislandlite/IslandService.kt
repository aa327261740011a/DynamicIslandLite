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

    companion object {
        var updateText: ((String) -> Unit)? = null
    }

    private lateinit var windowManager: WindowManager
    private lateinit var islandView: View

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        islandView = LayoutInflater.from(this)
            .inflate(R.layout.island_layout, null)

        val textView = islandView.findViewById<TextView>(R.id.islandText)

        updateText = { text ->
            textView.text = text
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params.y = 40   // notch ke paas position

        windowManager.addView(islandView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(islandView)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
