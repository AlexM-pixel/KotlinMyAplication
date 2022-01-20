package com.example.kotlinmyaplicationtasks.view.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmyaplicationtasks.R
import com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase.UploadAudioUseCase
import com.example.kotlinmyaplicationtasks.model.Song
import com.example.kotlinmyaplicationtasks.view.adapters.SongsAdapter
import com.example.kotlinmyaplicationtasks.viewModel.SongsViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_list_songs.*
import www.sanju.motiontoast.MotionToast
import java.io.File

class ListSongsActivity : AppCompatActivity(), SongsAdapter.OnClickSong {
    private var songsViewModel: SongsViewModel? = null

    private var songsAdapter: SongsAdapter? = null
    private var songList: List<Song> = mutableListOf()
    private var loadingProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_songs)
        supportActionBar?.hide()
        loadingProgressBar = findViewById(R.id.progress_loading_song)
        songsViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(SongsViewModel::class.java)
        initRecycler()
        songsViewModel?.getSongsList()
        songsViewModel?.listSongLiveData?.observe(this) { list ->
            songsAdapter?.setSongsList(list)
            songList = list
        }
        songsViewModel?.toastIvent?.observe(this) { msg ->
            showToast(msg)
        }
        observeLoadingEvent()
        val btn = findViewById<Button>(R.id.btn_nextActivity)
        btn.setOnClickListener {
            val intent = Intent(this, RemoteSongsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeLoadingEvent() {
        songsViewModel?.loadingEvent?.observe(this) { loading ->
            if (loading) {
                showLoadingDialog()
            } else hideDialog()
        }
    }

    fun initRecycler() {
        val mySongsRecycler = findViewById<RecyclerView>(R.id.recyclerView_songs_id)
        songsAdapter = SongsAdapter(this)
        mySongsRecycler.apply {
            adapter = songsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun selectCurrentSong(value: () -> Int) {
        //   songsViewModel?.saveSong(value)
        val uri: Uri = Uri.fromFile(File(songList[value].songsPath))
        songsViewModel?.uploadSong(value, uri)
        //   songsViewModel?.downloadAudioUrl(song = songList[value], callback ={_uri ->
        //       Toast.makeText(this, _uri.toString(), Toast.LENGTH_LONG).show()
        //    } )
    }

    private fun showToast(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()


        //        MotionToast.createColorToast(
//            this,
//            "Super!",
//            value,
//            MotionToast.TOAST_SUCCESS,
//            MotionToast.GRAVITY_BOTTOM,
//            MotionToast.LONG_DURATION,
//            ResourcesCompat.getFont(
//                this,
//                R.font.helvetica_regular
//            )
//        )
    }

    private fun showLoadingDialog() {
        if (progress_loading_song.isInvisible) {
            progress_loading_song.visibility = View.VISIBLE
        }
    }

    private fun hideDialog() {
        if (progress_loading_song.visibility == View.VISIBLE) {
            progress_loading_song.visibility = View.INVISIBLE
        }
    }
}