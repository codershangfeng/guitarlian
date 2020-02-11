package me.codershangfeng.guitarlian.ui.metronome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.codershangfeng.guitarlian.R
import me.codershangfeng.guitarlian.ui.metronome.MetronomeViewModel.Companion.DEFAULT_BEATS_PER_MINUTES
import me.codershangfeng.guitarlian.ui.metronome.MetronomeViewModel.Companion.MAX_BEATS_PER_MINUTES
import me.codershangfeng.guitarlian.ui.metronome.MetronomeViewModel.Companion.MIN_BEATS_PER_MINUTES

class MetronomeFragment : Fragment() {

    private lateinit var metronomeViewModel: MetronomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        metronomeViewModel =
            ViewModelProvider(this).get(MetronomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_metronome, container, false)

        val textView: TextView = root.findViewById(R.id.text_beats_per_minutes)

        val startBtn: Button = root.findViewById(R.id.start_btn)
        startBtn.setOnClickListener { Log.i(this::class.java.simpleName, "clicked") }

        val skbBpm: SeekBar = root.findViewById(R.id.skb_beats_per_minutes)
        skbBpm.max = MAX_BEATS_PER_MINUTES
        skbBpm.progress = DEFAULT_BEATS_PER_MINUTES
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
            textView.text = it
        })
        return root
    }
}