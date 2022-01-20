package com.example.kotlinmyaplicationtasks.viewModel

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmyaplicationtasks.common.Resource
import com.example.kotlinmyaplicationtasks.firebaseDownloadUseCase.ReceiveSongsUseCase
import com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase.DownloadAudioURL_UseCase
import com.example.kotlinmyaplicationtasks.model.Song
import com.example.kotlinmyaplicationtasks.repository.FirestorageRepositoryImpl
import com.example.kotlinmyaplicationtasks.repository.FirestoreRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemoteSongsViewModel : ViewModel() {
    private val listSongs = mutableListOf<Song>()
    private val remoteSongsUseCase: ReceiveSongsUseCase
        get() = ReceiveSongsUseCase(repository = FirestoreRepositoryImpl(FirebaseFirestore.getInstance()))
    private val downloadAudioUrlURLUseCase: DownloadAudioURL_UseCase
        get() = DownloadAudioURL_UseCase(repository = FirestorageRepositoryImpl(FirebaseStorage.getInstance()))

    private val _listSongLiveData: MutableLiveData<List<Song>> = MutableLiveData()
    val listSongLiveData: LiveData<List<Song>> get() = _listSongLiveData
    val toastEvent: MutableLiveData<String> = MutableLiveData()

    fun getRemoteList() {
        remoteSongsUseCase()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> toastEvent.postValue("loading")
                    is Resource.Success -> {
                        toastEvent.postValue("success!")
                        _listSongLiveData.postValue(result.data!!)
                        listSongs.addAll(result.data)
                    }
                    is Resource.Error -> toastEvent.postValue(result.message.toString())
                    else -> toastEvent.postValue(result.message.toString())
                }
            }.launchIn(viewModelScope)
    }

    fun startPlay(uri: Uri?) {
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(uri.toString())
            prepare() // might take long! (for buffering, etc)
            start()
        }
    }

    fun downloadAudioUrl(song: Song, callback: (uri: Uri?) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            downloadAudioUrlURLUseCase(song).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        toastEvent.postValue("loading")
                    }
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            callback(result.data)
                            toastEvent.postValue(result.data.toString().plus("in Success!"))
                            startPlay(result.data)
                        }
                        toastEvent.value = "success"
                    }
                    is Resource.Error -> toastEvent.postValue("error download Uri")
                }

            }
        }
}