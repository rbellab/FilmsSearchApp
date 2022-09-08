package eu.berngardt.filmssearch.viewmodel

import java.net.URL
import android.graphics.Bitmap
import kotlin.coroutines.resume
import androidx.lifecycle.ViewModel
import android.graphics.BitmapFactory
import kotlin.coroutines.suspendCoroutine


class DetailsFragmentViewModel : ViewModel() {
    suspend fun loadWallpaper(url: String): Bitmap {
        return suspendCoroutine {
            val url = URL(url)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            it.resume(bitmap)
        }
    }
}