package com.arnold.sleepmonitor.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arnold.sleepmonitor.databinding.FragmentHomeBinding
import com.arnold.sleepmonitor.recorder.LightRecorder
import com.arnold.sleepmonitor.recorder.LinearAccRecorder
import com.arnold.sleepmonitor.recorder.VoiceRecorder

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val TAG = "HomeFragment"

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val buttonVoice: Button = binding.buttonVoice
        var buttonVoicePressed = false
        buttonVoice.setOnClickListener() {
            Log.d(TAG, "Voice Button clicked")
            buttonVoicePressed = !buttonVoicePressed


            if (buttonVoicePressed) {
                buttonVoice.text = "Stop Voice"
                VoiceRecorder.startSensor()
            } else {
                buttonVoice.text = "Start Voice"
                VoiceRecorder.stopSensor()
            }
        }

        val buttonSensors: Button = binding.buttonSensors
        var buttonSensorPressed = false
        buttonSensors.setOnClickListener() {
            Log.d(TAG, "Sensors Button clicked")
            buttonSensorPressed = !buttonSensorPressed

            if (buttonSensorPressed) {
                buttonSensors.text = "Stop Sensors"
                LightRecorder.startSensor()
                LinearAccRecorder.startSensor()
            } else {
                buttonSensors.text = "Start Sensors"
                LightRecorder.stopSensor()
                LinearAccRecorder.stopSensor()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}