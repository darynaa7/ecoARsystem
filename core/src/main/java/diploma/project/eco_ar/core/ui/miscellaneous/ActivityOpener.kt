package diploma.project.eco_ar.core.ui.miscellaneous

import android.app.PendingIntent
import android.content.Context

interface ActivityOpener {
    fun openMainActivity(context: Context)
    fun pendingIntentForMainActivity(context: Context): PendingIntent
}