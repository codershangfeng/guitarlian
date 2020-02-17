package me.codershangfeng.guitarlian.ui.tune

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.codershangfeng.guitarlian.R


private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class TuneFragment : Fragment() {

    private lateinit var tuneViewModel: TuneViewModel

    private var permissionToRecordAccepted = false
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)


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
        val root = inflater.inflate(R.layout.fragment_tune, container, false)

        bindingText(root)

        return root
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

    private fun bindingText(root: View) {
        val textView: TextView = root.findViewById(R.id.text_home)
        tuneViewModel.frequency.observe(this, Observer {
            textView.text = it.toString()
        })
    }
}