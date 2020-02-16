package me.codershangfeng.guitarlian.ui.tune

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.codershangfeng.guitarlian.R

class TuneFragment : Fragment() {

    private lateinit var tuneViewModel: TuneViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tuneViewModel =
            ViewModelProvider(this).get(TuneViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_tune, container, false)

        bindingText(root)

        

        return root
    }

    private fun bindingText(root: View) {
        val textView: TextView = root.findViewById(R.id.text_home)
        tuneViewModel.frequency.observe(this, Observer {
            textView.text = it.toString()
        })
    }
}