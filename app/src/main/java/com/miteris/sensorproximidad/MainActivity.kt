package com.miteris.sensorproximidad

import android.content.ContentValues.TAG
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val proximitySensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if(proximitySensor == null){
            Log.e(TAG, "No tiene sensor de proximidad")
            finish()
        }

        val proximitySensorListener: SensorEventListener = object: SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                if (sensorEvent.values[0] < proximitySensor.maximumRange){
                    window.decorView.setBackgroundColor(Color.RED)
                    playSoundGato()
                } else {
                    window.decorView.setBackgroundColor(Color.GREEN)
                    stopSound()
                }
            }
            override fun onAccuracyChanged(sensor: Sensor, i: Int) {  }
        }

        // Actualizar en microsegundos

        //en que carpetas hay cosas, propiedades comunes boton, traduccion, leer el tema 1, funciones con operaciones, cuando son nulas
        //asset studio
        sensorManager.registerListener(
            proximitySensorListener, proximitySensor, 2 * 1000 * 1000
        )

        // Creamos variable
        var mMediaPlayer: MediaPlayer? = null

        // Creamos la función play
        fun playSoundGato(){
            if(mMediaPlayer == null){
                mMediaPlayer = MediaPlayer.create(this, R.raw.gato)
                mMediaPlayer !!.isLooping = true
                mMediaPlayer !!.start()
            } else mMediaPlayer!!.start()
        }

        fun pauseSound(){
            if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
        }

        fun stopSound(){
            super.onStop()
            if (mMediaPlayer != null){
                mMediaPlayer!!.release()
                mMediaPlayer = null
            }
        }

    }

    // Creamos variable
    var mMediaPlayer: MediaPlayer? = null

    // Creamos la función play
    fun playSoundGato(){
        if(mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(this, R.raw.gato)
            mMediaPlayer !!.isLooping = true
            mMediaPlayer !!.start()
        } else mMediaPlayer!!.start()
    }

    fun pauseSound(){
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }

    fun stopSound(){
        super.onStop()
        if (mMediaPlayer != null){
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

}