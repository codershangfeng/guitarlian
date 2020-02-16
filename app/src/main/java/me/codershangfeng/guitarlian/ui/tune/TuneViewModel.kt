package me.codershangfeng.guitarlian.ui.tune

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TuneViewModel : ViewModel() {

    private val _frequency = MutableLiveData<Double>().apply {
        value = 44100.0
    }
    val frequency: MutableLiveData<Double> = _frequency
}