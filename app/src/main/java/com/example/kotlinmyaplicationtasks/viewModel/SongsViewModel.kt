package com.example.kotlinmyaplicationtasks.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinmyaplicationtasks.R
import com.example.kotlinmyaplicationtasks.common.Resource
import com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase.DownloadAudioURL_UseCase
import com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase.SaveSongUseCase
import com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase.UploadAudioUseCase
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

class SongsViewModel(application: Application) :
    AndroidViewModel(application) {
    private val uploadAudioUseCase: UploadAudioUseCase
        get() = UploadAudioUseCase(repository = FirestorageRepositoryImpl(FirebaseStorage.getInstance()))
    private val saveSongUseCase: SaveSongUseCase
        get() = SaveSongUseCase(FirestoreRepositoryImpl(FirebaseFirestore.getInstance()))
    private val downloadAudioUrlURLUseCase: DownloadAudioURL_UseCase
        get() = DownloadAudioURL_UseCase(repository = FirestorageRepositoryImpl(FirebaseStorage.getInstance()))
    private val listSongs = mutableListOf<Song>()
    val listSongLiveData: MutableLiveData<List<Song>> = MutableLiveData()
    val toastIvent: MutableLiveData<String> = MutableLiveData()

    private val _loadingEvent = MutableLiveData(false)
    val loadingEvent: LiveData<Boolean> get() = _loadingEvent


    //    private val fireStorageRepositoryImpl: FirestorageRepositoryImpl =
//        FirestorageRepositoryImpl(FirebaseStorage.getInstance())

    val contentResolver by lazy {
        application.contentResolver
    }

    @SuppressLint("Range", "Recycle")
    fun getSongsList() {
        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null,
            null
        )
        cursor?.apply {
            while (moveToNext()) {
                val musicPath = getString(getColumnIndex(MediaStore.Audio.Media.DATA))
                val musicName = getString(getColumnIndex(MediaStore.Audio.Media.TITLE))
                val musicDuration = getString(getColumnIndex(MediaStore.Audio.Media.DURATION))
                val song_id = musicName ?: "empty"
                val song = Song(
                    id = song_id,
                    songsName = musicName,
                    songsPath = musicPath,
                    imageUri = R.drawable.icons_music48,
                    songsDuration = musicDuration.toLong(),
                    storageLink = "null"
                )
                listSongs.add(song)
                Log.d("retrofit_tag", "$musicName: $musicPath, $musicDuration")
            }
        }
        listSongLiveData.value = listSongs
    }

//    fun mySaveSong(value: Int) {
//        val uri: Uri = Uri.fromFile(File(listSongs[value].songsPath))
//        val id = System.currentTimeMillis().toString()
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                fireStorageRepositoryImpl.upLoadAudio(id, uri)
//                toastIvent.value = id
//            } catch (e: Exception) {
//                e.message?.let {
//                    Log.e("Music", it)
//                    toastIvent.value = it
//                }
//            }
//        }
//    }

    fun uploadSong(value: Int, uri: Uri) = viewModelScope.launch(Dispatchers.IO) {
        uploadAudioUseCase(listSongs[value], uri = uri).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    toastIvent.postValue("loading")
                    _loadingEvent.postValue(true)
                    Log.e("retrofit_tag", "loading: ${result.message}")
                }
                is Resource.Success -> {
                    val songUrl = result.uploadSongTask?.uploadSessionUri
                    val songDuration = result.uploadSongTask?.totalByteCount
                    //Log.e("retrofit_tag", "success:::: ${songUrl?.result}")
                    saveTrack(listSongs[value],songUrl, songDuration!!)     //сохранение в базу данных трэка с ссылкой на него в хранилище
                    toastIvent.postValue("success!!!  $songUrl")
                    _loadingEvent.postValue(false)
                }
                is Resource.Error -> {
                    _loadingEvent.postValue(false)
                    toastIvent.value = result.message ?: "error"
                    Log.e("retrofit_tag", "error: ${result.message}")
                }
                is Resource.AccessDenied -> {
                    Log.e("retrofit_tag", "error: access")
                    toastIvent.postValue("error: access ${result.message}")
                    _loadingEvent.postValue(false)
                }
            }
        }
    }


    fun saveTrack(song: Song, songUrl: Uri?, songDuration: Long) {
        val mapSong = Song(
            id = song.id,
            songsName = song.songsName,
            songsPath = song.songsPath,
            imageUri = song.imageUri,
            songsDuration = songDuration,
            storageLink = songUrl.toString()
        )
        saveSongUseCase(mapSong)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.e("retrofit_tag", result.message ?: "loading Track")
                    }
                    is Resource.Success -> {
                        Log.e("retrofit_tag", result.message ?: "success Track")
                    }
                    is Resource.Error -> {
                        Log.e("retrofit_tag", result.message ?: "error Track")
                    }
                    else -> {
                        Log.e("retrofit_tag", result.message ?: "else Track")
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun downloadAudioUrl(song: Song, callback: (uri: Uri?) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            downloadAudioUrlURLUseCase(song).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        toastIvent.postValue("loading")
                    }
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            callback(result.data)
                        }
                        toastIvent.value = "success"
                    }
                    is Resource.Error -> toastIvent.postValue("error download Uri")
                }

            }
        }

}