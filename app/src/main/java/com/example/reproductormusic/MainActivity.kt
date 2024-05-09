package com.example.reproductormusic

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.reproductormusic.AppConstant.Companion.CURREN_INDEX_SONG
import com.example.reproductormusic.AppConstant.Companion.IS_PLAYING
import com.example.reproductormusic.AppConstant.Companion.LOG_MAIN_ACTIVITY
import com.example.reproductormusic.AppConstant.Companion.MEDIA_PLAYER_POSITION
import com.example.reproductormusic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
     private lateinit var binding: ActivityMainBinding
     private var mediaPlayer: MediaPlayer? = null
     private var position: Int = 0
     private var currentSongIndex: Int =0
     private lateinit var  currentSong: Song
     private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(LOG_MAIN_ACTIVITY,"onCreate()")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentSong = AppConstant.songs[currentSongIndex]

        savedInstanceState?.let {
            position = it.getInt(MEDIA_PLAYER_POSITION)
            isPlaying = it.getBoolean(IS_PLAYING)
            currentSongIndex = it.getInt(CURREN_INDEX_SONG)
        }
        updateUiSong()
        binding.playPauseButton.setOnClickListener {
            playOrPauseMusic()
        }
        binding.playNextButton.setOnClickListener { playNextSong() }
        binding.playPreviousButton.setOnClickListener { playPreviousSong() }
    }

    override fun onStart() {
        super.onStart()
        Log.i(LOG_MAIN_ACTIVITY,"onStart()")
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        if (isPlaying) mediaPlayer?.start()

    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_MAIN_ACTIVITY,"nResume()")
        mediaPlayer?.seekTo(position)
        if (isPlaying) {
            isPlaying = true
            mediaPlayer?.start()
        }

    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_MAIN_ACTIVITY,"onPause()")
        if( mediaPlayer != null)
            position = mediaPlayer!!.currentPosition
            mediaPlayer?.pause()

    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_MAIN_ACTIVITY,"onStop()")
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(LOG_MAIN_ACTIVITY,"nRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_MAIN_ACTIVITY,"onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MEDIA_PLAYER_POSITION, position)
        outState.putBoolean(IS_PLAYING,isPlaying)
        outState.putInt(CURREN_INDEX_SONG,currentSongIndex)
    }

    private fun updateUiSong(){
        binding.titleTextView.text = currentSong.title
        binding.albumCoverImageView.setImageResource(currentSong.imageResId)
        updatePlayPauseButton()
    }

    private fun  playOrPauseMusic(){
        if (isPlaying){
            mediaPlayer?.pause()
        }else{
            mediaPlayer?.start()
        }
        isPlaying = !isPlaying
        updatePlayPauseButton()
    }

    private fun updatePlayPauseButton (){
        binding.playPauseButton.text = if(isPlaying)"Pause" else "Play"
    }

    private fun playNextSong(){
        currentSongIndex = (currentSongIndex + 1) % AppConstant.songs.size
        currentSong = AppConstant.songs[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this,currentSong.audioResId)
        mediaPlayer?.start()
        isPlaying = true
        updateUiSong()
    }
    private fun playPreviousSong(){
        // Algoritmo para obtener el indice y hacer una lista circular
        //cancion anterior - tamaño lista de canciones pra que siempre sea positivo
        //% devuelve un número positico si el dividendo es negativo
        currentSongIndex = (currentSongIndex - 1 + AppConstant.songs.size) % AppConstant.songs.size
        currentSong = AppConstant.songs[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        mediaPlayer?.start()
        isPlaying = true
        updateUiSong()
    }


}