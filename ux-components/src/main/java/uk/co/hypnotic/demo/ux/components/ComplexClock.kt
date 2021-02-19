package uk.co.hypnotic.demo.ux.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView

class ComplexClock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.complex_clock, this)
    }

    fun setSeconds(seconds : Int) {
        val clock = findViewById<Clock>(R.id.clock)
        clock.setSeconds(seconds)
        val text = findViewById<TextView>(R.id.seconds)
        text.text = seconds.toString()
    }

}