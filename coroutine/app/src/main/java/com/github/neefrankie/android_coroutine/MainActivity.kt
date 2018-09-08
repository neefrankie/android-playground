package com.github.neefrankie.android_coroutine

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

fun View.onClick(action: suspend (View) -> Unit) {
//    setOnClickListener {
//        launch(UI) {
//            action()
//        }
//    }
    val eventActor = actor<View>(UI) {
        for (event in channel) action(event)
    }

    setOnClickListener {
        eventActor.offer(it)
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        setup()
    }

    private fun setup() {
//        val job = launch(UI) {
//            for (i in 10 downTo 1) {
//                textView.text = "Countdown $i ..."
//                textView2.text = "Countdown $i ..."
//                delay(500)
//            }
//            textView.text="Done!"
//        }

        fab.onClick {
            for (i in 10 downTo 1) {
                textView.text = "Countdown $i ..."
                textView2.text = "Countdown $i ..."
                delay(500)
            }
            textView2.text="Done!"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
