package com.github.neefrankie.fullscreentest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import awaitString
import com.github.kittinunf.fuel.Fuel
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.info

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity(), AnkoLogger {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adContainer = View.inflate(this, R.layout.overlay, null)
        val fullscreenAd = adContainer.findViewById<ImageView>(R.id.fullscreen_ad)
        main_container.addView(adContainer)
        supportActionBar?.hide()

        fullscreenAd.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        fullscreenAd.setOnClickListener {
            main_container.removeView(adContainer)
            supportActionBar?.show()
            main_container.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }

        launch(UI) {
            for (i in 1..10) {
                info("Loop $i")
                delay(1000)
            }

            val result = bg {
                Ion.with(this@FullscreenActivity)
                        .load("https://api003.ftmailbox.com/index.php/jsapi/applaunchschedule")
                        .asString()
                        .get()
            }
            info(result.await())
        }

        info("onCreate finished")
    }

    override fun onResume() {
        super.onResume()
        info("onResume finished")
    }

    override fun onStart() {
        super.onStart()
        info("onStart finished")
    }

    override fun onPause() {
        super.onPause()
        info("onPause finished")
    }

    override fun onStop() {
        super.onStop()
        info("onStop finished")
    }
}
