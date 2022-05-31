package eu.berngardt.filmssearch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import eu.berngardt.filmssearch.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_DELAY = 2000L
    }

    private var _binding: ActivitySplashScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(Runnable {
            val intenr = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intenr)
            finish()
        }, SPLASH_DELAY)
    }
}