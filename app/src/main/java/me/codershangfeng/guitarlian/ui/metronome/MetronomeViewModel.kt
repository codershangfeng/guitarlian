package me.codershangfeng.guitarlian.ui.metronome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MetronomeViewModel : ViewModel() {

    companion object {
        const val MAX_BEATS_PER_MINUTES = 178
        const val DEFAULT_BEATS_PER_MINUTES = 100
        const val MIN_BEATS_PER_MINUTES = 40
        const val ONE_MINUTE_IN_MILLISECOND: Long = 60_000
    }

    private val _beatsPerMinute = MutableLiveData<String>(DEFAULT_BEATS_PER_MINUTES.toString())
    val beatsPerMinute: MutableLiveData<String> = _beatsPerMinute
}

