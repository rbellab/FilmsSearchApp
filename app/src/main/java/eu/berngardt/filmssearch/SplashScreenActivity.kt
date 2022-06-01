package eu.berngardt.filmssearch

import android.os.Bundle
import android.os.Handler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import eu.berngardt.filmssearch.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DELAY = 2000L
    }

    private var _binding: ActivitySplashScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, SPLASH_DELAY)
    }
}