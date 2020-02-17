package me.codershangfeng.guitarlian.ui.tune

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.codershangfeng.guitarlian.R

private const val LOG_TAG = "TuneFragment"
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class TuneFragment : Fragment() {
    private lateinit var tuneViewModel: TuneViewModel

    private var permissionToRecordAccepted = false
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)

    private var playButton: PlayButton? = null
    private var frequencyTextView: FrequencyTextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tuneViewModel =
            ViewModelProvider(this).get(TuneViewModel::class.java)

        ActivityCompat.requestPermissions(
            this.requireActivity(),
            permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )

        playButton = PlayButton(context)
        frequencyTextView = FrequencyTextView(context)

        return LinearLayout(requireContext()).apply {
            gravity = Gravity.CENTER
            orientation = LinearLayout.VERTICAL

            addView(
                frequencyTextView, LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0f
                )
            )
            addView(
                playButton, LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0f
                )
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted =
            if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) grantResults[0] == PackageManager.PERMISSION_GRANTED else false

        if (!permissionToRecordAccepted) requireActivity().finish()
    }

    internal inner class PlayButton(ctx: Context? = requireContext()) : Button(ctx) {

        private val clickListener = OnClickListener {
            Log.i(LOG_TAG, "Clicked")
        }

        init {
            text = resources.getText(R.string.text_play)
            setOnClickListener(clickListener)
        }
    }

    internal inner class FrequencyTextView(ctx: Context? = requireContext()) : TextView(ctx) {
        init {
            tuneViewModel.frequency.observe(this@TuneFragment, Observer {
                text = it.toString()
            })
        }
    }
}