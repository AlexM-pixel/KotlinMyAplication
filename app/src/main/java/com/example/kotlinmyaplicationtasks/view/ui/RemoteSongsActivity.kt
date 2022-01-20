package com.example.kotlinmyaplicationtasks.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmyaplicationtasks.R
import com.example.kotlinmyaplicationtasks.model.Song
import com.example.kotlinmyaplicationtasks.view.adapters.SongsAdapter
import com.example.kotlinmyaplicationtasks.viewModel.RemoteSongsViewModel

class RemoteSongsActivity : AppCompatActivity(), SongsAdapter.OnClickSong {
    private var songsAdapter: SongsAdapter? = null
    private var remoteViewModel: RemoteSongsViewModel? = null
    private val listSongs = mutableListOf<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_songs)
        remoteViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(RemoteSongsViewModel::class.java)
        initRecycler()
        remoteViewModel?.getRemoteList()
        remoteViewModel?.listSongLiveData?.observe(this) { list ->
            songsAdapter?.setSongsList(list)
            listSongs.addAll(list)
        }
        remoteViewModel?.toastEvent?.observe(this) { msg ->
            showToast(msg)
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initRecycler() {
        val recycler: RecyclerView = findViewById(R.id.remote_songs_rv)
        songsAdapter = SongsAdapter(this)
        recycler.apply {
            adapter = songsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun selectCurrentSong(value: Int) {
        remoteViewModel?.downloadAudioUrl(listSongs[value], callback = { call ->
            Toast.makeText(this, call.toString(), Toast.LENGTH_SHORT).show()
        })
        Log.e("","list.size = ".plus(listSongs.size.toString()))
    }


}