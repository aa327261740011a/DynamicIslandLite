package com.example.dynamicislandlite

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.content.Intent

class MusicNotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notification = sbn.notification
        val extras = notification.extras

        val title = extras.getString("android.title") ?: return
        val text = extras.getString("android.text") ?: ""

        // Simple check for music apps
        if (isMusicApp(sbn.packageName)) {
            val intent = Intent(this, IslandService::class.java)
            intent.putExtra("title", title)
            intent.putExtra("text", text)
            startService(intent)
        }
    }

    private fun isMusicApp(packageName: String): Boolean {
        return packageName.contains("spotify")
                || packageName.contains("music")
                || packageName.contains("youtube")
                || packageName.contains("wynk")
                || packageName.contains("gaana")
    }
}
