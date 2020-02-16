package me.codershangfeng.guitarlian.ui.metronome

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.codershangfeng.guitarlian.R
import me.codershangfeng.guitarlian.ui.metronome.MetronomeViewModel.Companion.DEFAULT_BEATS_PER_MINUTES
import me.codershangfeng.guitarlian.ui.metronome.MetronomeViewModel.Companion.MAX_BEATS_PER_MINUTES
import me.codershangfeng.guitarlian.ui.metronome.MetronomeViewModel.Companion.MIN_BEATS_PER_MINUTES
import me.codershangfeng.guitarlian.ui.metronome.MetronomeViewModel.Companion.ONE_MINUTE_IN_MILLISECOND
import java.util.*

class MetronomeFragment : Fragment() {

    private lateinit var metronomeViewModel: MetronomeViewModel

    private lateinit var timer: Timer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        metronomeViewModel =
            ViewModelProvider(this).get(MetronomeViewModel::class.java)
        timer = Timer()

        val root = inflater.inflate(R.layout.fragment_metronome, container, false)

        bindingText(root)

        bindingBtn(root)

        bindingBar(root)

        return root
    }

    private fun bindingText(root: View) {
        val textView: TextView = root.findViewById(R.id.text_beats_per_minutes)
        metronomeViewModel.beatsPerMinute.observe(this, Observer {
            textView.text = it
        })
    }

    private fun bindingBtn(root: View) {
        val startBtn: ImageButton = root.findViewById(R.id.start_btn)
        val generator = ToneGenerator(AudioManager.STREAM_RING, 100)


        startBtn.setOnClickListener {
            timer.cancel()
            timer = Timer()
            val timerTask = object : TimerTask() {
                override fun run() {
                    generator.startTone(ToneGenerator.TONE_PROP_BEEP)
                }
            }
            // TODO: Extract these business logic to ViewModel layer later
            val bpm = metronomeViewModel.beatsPerMinute.value?.toInt() ?: DEFAULT_BEATS_PER_MINUTES
            timer.scheduleAtFixedRate(
                timerTask, 0, ONE_MINUTE_IN_MILLISECOND / bpm
            )
        }
    }

    private fun bindingBar(root: View) {
        val skbBpm: SeekBar = root.findViewById(R.id.skb_beats_per_minutes)
        // TODO: Extract these business logic to ViewModel layer later
        skbBpm.max = MAX_BEATS_PER_MINUTES
        skbBpm.progress = DEFAULT_BEATS_PER_MINUTES - MIN_BEATS_PER_MINUTES
        skbBpm.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                timer.cancel()
                metronomeViewModel.beatsPerMinute.value =
                    (progress + MIN_BEATS_PER_MINUTES).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // empty body
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // empty body
            }
        })
    }

}