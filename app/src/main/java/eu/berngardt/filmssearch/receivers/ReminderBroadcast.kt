package eu.berngardt.filmssearch.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import eu.berngardt.filmssearch.data.entity.Film
import eu.berngardt.filmssearch.view.notifications.NotificationConstants
import eu.berngardt.filmssearch.view.notifications.NotificationHelper

class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val bundle = intent?.getBundleExtra(NotificationConstants.FILM_BUNDLE_KEY)
        val film: Film = bundle?.get(NotificationConstants.FILM_KEY) as Film

        NotificationHelper.createNotification(context, film)
    }
}