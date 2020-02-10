package me.codershangfeng.guitarlian.ui.metronome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.codershangfeng.guitarlian.R

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

        metronomeViewModel.beatsPerMinute.observe(this, Observer {
            textView.text = it
        })

        val startBtn: Button = root.findViewById(R.id.start_btn)

        startBtn.setOnClickListener { v -> Log.i(this::class.java.simpleName, "clicked" ) }

        return root
    }
}