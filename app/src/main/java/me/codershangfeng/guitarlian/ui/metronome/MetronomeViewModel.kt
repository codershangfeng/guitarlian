package me.codershangfeng.guitarlian.ui.metronome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MetronomeViewModel : ViewModel() {
    private val _beatsPerMinute = MutableLiveData<String>().apply {
        value = "60"
    }
    val beatsPerMinute: LiveData<String> = _beatsPerMinute
}