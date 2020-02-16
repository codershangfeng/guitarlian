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

    private val generator = ToneGenerator(AudioManager.STREAM_RING, 100)

    private var isPlaying: Boolean = false

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
        val bpmText: TextView = root.findViewById(R.id.text_beats_per_minutes)
        val maxBpmText: TextView = root.findViewById(R.id.text_maximum_bpm)
        val minBpmText: TextView = root.findViewById(R.id.text_minimum_bpm)

        maxBpmText.text = MAX_BEATS_PER_MINUTES.toString()
        minBpmText.text = MIN_BEATS_PER_MINUTES.toString()

        metronomeViewModel.beatsPerMinute.observe(this, Observer {
            bpmText.text = it
        })
    }

    private fun bindingBtn(root: View) {
        val startBtn: ImageButton = root.findViewById(R.id.start_btn)
        val stopBtn: ImageButton = root.findViewById(R.id.stop_btn)
        val plusBtn: ImageButton = root.findViewById(R.id.plus_btn)
        val minusBtn: ImageButton = root.findViewById(R.id.minus_btn)

        startBtn.setOnClickListener {
            startBtn.visibility = View.GONE
            stopBtn.visibility = View.VISIBLE
            startMetronome()
        }

        stopBtn.setOnClickListener {
            stopBtn.visibility = View.GONE
            startBtn.visibility = View.VISIBLE
            stopMetronome()
        }

        plusBtn.setOnClickListener {
            metronomeViewModel.beatsPerMinute.value =
                metronomeViewModel.beatsPerMinute.value?.toInt()?.plus(1).toString()
        }

        minusBtn.setOnClickListener {
            metronomeViewModel.beatsPerMinute.value =
                metronomeViewModel.beatsPerMinute.value?.toInt()?.minus(1).toString()
        }
    }

    private fun bindingBar(root: View) {
        val skbBpm: SeekBar = root.findViewById(R.id.skb_beats_per_minutes)
        // TODO: Extract these business logic to ViewModel layer later
        skbBpm.max = MAX_BEATS_PER_MINUTES
        skbBpm.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
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

        metronomeViewModel.beatsPerMinute.observe(this, Observer {
            skbBpm.progress = it.toInt() - MIN_BEATS_PER_MINUTES
            if (isPlaying) updateMetronome(it.toInt())
        })
    }

    private fun startMetronome() {
        isPlaying = true
        updateMetronome(metronomeViewModel.beatsPerMinute.value?.toInt())
    }

    private fun stopMetronome() {
        isPlaying = false
        timer.cancel()
    }

    private fun updateMetronome(bpm: Int? = DEFAULT_BEATS_PER_MINUTES) {
        timer.cancel()
        timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                generator.startTone(ToneGenerator.TONE_PROP_BEEP)
            }
        }
        // TODO: Extract these calculate business logic to ViewModel layer later
        timer.scheduleAtFixedRate(
            timerTask, 0, ONE_MINUTE_IN_MILLISECOND / (bpm ?: DEFAULT_BEATS_PER_MINUTES)
        )
    }

}