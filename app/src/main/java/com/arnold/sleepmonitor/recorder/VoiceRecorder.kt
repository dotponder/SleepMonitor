package com.arnold.sleepmonitor.recorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.arnold.sleepmonitor.MApplication
import com.arnold.sleepmonitor.MainActivity
import kotlin.math.log10

object VoiceRecorder: HandlerThread("VoiceRecorder") {
    private val TAG = "VoiceRecorder"
    private var handler: Handler? = null
    private var sensorThread: HandlerThread? = null
    private var sensorHandler: Handler? = null
    
    val SAMPLE_RATE = 44100
    val BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT)

    var audioRecord: AudioRecord? = null
    var isRecording = false
    
    fun startSensor() {
        Log.i(TAG, "Starting sensor")
        sensorThread = HandlerThread(TAG, Thread.NORM_PRIORITY)
        sensorThread!!.start()
        sensorHandler = Handler(sensorThread!!.looper)
        // start
        sensorInit()
        sensorHandler!!.post(runnable)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun stopSensor() {
        Log.i(TAG, "Stopping sensor")
        // stop
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null

        sensorThread!!.quitSafely()
    }

    fun sensorInit() {
        if (ActivityCompat.checkSelfPermission(
                MApplication.context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "No permission to record audio.")
            ActivityCompat.requestPermissions(
                MainActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
            return
        }
        Log.i(TAG, "Permission to record audio have already granted.")

        audioRecord = AudioRecord(
            android.media.MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_DEFAULT,
            AudioFormat.ENCODING_PCM_16BIT,
            BUFFER_SIZE
        )
        if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
            Log.i(TAG, "AudioRecord can't initialize!")
            return
        }
        isRecording = true
    }

    fun setHandler(handler: Handler) {
        this.handler = handler
    }

    fun sendMessage(event: MSensorEvent) {
        if (handler == null) {
            return
        }

        handler?.obtainMessage(event.type.ordinal, event)?.apply {
            sendToTarget()
        }
    }

    private val runnable = Runnable {
        audioRecord?.startRecording()
        val buffer = ShortArray(BUFFER_SIZE)
        while (isRecording) {
            val bufferLength = audioRecord?.read(buffer, 0, BUFFER_SIZE)
            if (bufferLength != null) {
                var sum = 0.0
                var reverseCount = 0
                for (i in buffer.indices) {
                    // sum of square
                    sum += buffer[i] * buffer[i]
                    if (i < bufferLength - 1 && buffer[i] <= 0 && buffer[i+1] >= 0){
                        reverseCount++
                    }
                }
                if (bufferLength > 0) {
                    // mean
                    var volume = sum / bufferLength
                    // to decibel
                    volume = 10 * log10(volume)
                    Log.i(TAG, "Current volume: $volume (dB)")

                    val time: Double = bufferLength.toDouble() / SAMPLE_RATE.toDouble()
                    val frequency:Double = reverseCount.toDouble() / time
                    Log.i(TAG, "Time spend: $time (s), buffer length: $bufferLength, frequency: $frequency (Hz), reverse count: $reverseCount")

                    val msgEvent = MSensorEvent()
                    msgEvent.type = MSensorType.VOICE
                    msgEvent.value1 = volume.toString()
                    msgEvent.value2 = frequency.toString()
                    sendMessage(msgEvent)
                }
            }

            Thread.sleep(100)
        }
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }
}