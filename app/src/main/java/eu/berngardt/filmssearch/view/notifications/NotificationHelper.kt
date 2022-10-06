package eu.berngardt.filmssearch.view.notifications

import android.content.Intent
import android.graphics.Bitmap
import android.content.Context
import com.bumptech.glide.Glide
import android.app.PendingIntent
import eu.berngardt.filmssearch.R
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import eu.berngardt.filmssearch.data.entity.Film
import eu.berngardt.filmssearch.view.MainActivity
import androidx.core.app.NotificationManagerCompat
import eu.berngardt.remote_module.entity.ApiConstants
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object NotificationHelper {
    private const val IMAGE_SIZE = "w500"

    fun createNotification(context: Context, film: Film) {
        val mIntent = Intent(context, MainActivity::class.java)

        val pendingIntent =
            PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context!!, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_outline_watch_later_24)
            setContentTitle("Не забудьте посмотреть!")
            setContentText(film.title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        val notificationManager = NotificationManagerCompat.from(context)


        Glide.with(context)
            // говорим что нужен битмап
            .asBitmap()
            // указываем откуда загружать, это ссылка как на загрузку с API
            .load(StringBuilder()
                .append(ApiConstants.IMAGES_URL)
                .append(IMAGE_SIZE)
                .append(film.poster))
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                // Этот коллбэк отрабатоет когда мы успешно получим битмап
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Создаем нотификации в стиле big picture
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    // Обновляем нотификацю
                    notificationManager.notify(film.id, builder.build())
                }
            })
        // Отправляем изначальную нотификацю в стандартном исполнении
        notificationManager.notify(film.id, builder.build())
    }

    fun notificationSet(context: Context, film: Film) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(
            context,
            { _, dpdYear, dpdMonth, dayOfMonth ->
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, pickerMinute ->
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(
                            dpdYear,
                            dpdMonth,
                            dayOfMonth,
                            hourOfDay,
                            pickerMinute,
                            0
                        )
                        val dateTimeInMillis = pickedDateTime.timeInMillis
                        // После того, как получим время, вызываем метод, который создаст Alarm
                        createWatchLaterEvent(context, dateTimeInMillis, film)
                    }

                TimePickerDialog(
                    context,
                    timeSetListener,
                    currentHour,
                    currentMinute,
                    true
                ).show()

            },
            currentYear,
            currentMonth,
            currentDay
        ).show()
    }

    private fun createWatchLaterEvent(context: Context, dateTimeInMillis: Long, film: Film) {
        // Получаем доступ к AlarmManager
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Создаем интент для запуска ресивера
        val intent = Intent(film.title, null, context, ReminderBroadcast()::class.java)
        // Кладем в него фильм
        val bundle = Bundle()
        bundle.putParcelable(NotificationConstants.FILM_KEY, film)
        intent.putExtra(NotificationConstants.FILM_BUNDLE_KEY, bundle)
        // Создаем пендинг интент для запуска извне приложения
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        // Устанавливаем Alarm
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            dateTimeInMillis,
            pendingIntent
        )
    }
}