package uk.co.hypnotic.demo.customviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uk.co.hypnotic.demo.ux.components.ComplexClock
import java.util.*

class ComplexClockFragment : Fragment() {
    private val refreshFrequency = 1000L

    private val updateCallback = Runnable { updateView() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_complex_clock, container, false)
    }

    override fun onResume() {
        super.onResume()
        updateView()
    }

    override fun onPause() {
        super.onPause()
        view?.removeCallbacks(updateCallback)
    }

    private fun updateView() {
        val complexClock = view?.findViewById<ComplexClock>(R.id.complexClock)
        complexClock?.setSeconds(Calendar.getInstance().get(Calendar.SECOND))
        view?.postDelayed(updateCallback, refreshFrequency)
    }
}