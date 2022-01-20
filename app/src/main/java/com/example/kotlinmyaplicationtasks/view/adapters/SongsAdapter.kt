package com.example.kotlinmyaplicationtasks.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmyaplicationtasks.R
import com.example.kotlinmyaplicationtasks.model.Song

class SongsAdapter(_listener: OnClickSong) : RecyclerView.Adapter<SongsAdapter.MySongsHolder>() {
    private var nameList: MutableList<Song>? = mutableListOf()
    private var listener: OnClickSong? = null

    init {
        listener = _listener
    }

    interface OnClickSong {
        fun selectCurrentSong(value: Int)
    }

    inner class MySongsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameSong = itemView.findViewById<TextView>(R.id.name_song_id)
        val cardView = itemView.findViewById<CardView>(R.id.cardView_song_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySongsHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.songs_item, parent, false)
        return MySongsHolder(itemView)
    }

    override fun onBindViewHolder(holder: MySongsHolder, position: Int) {
        holder.nameSong.text = nameList?.get(position)?.songsName
        holder.cardView.setOnClickListener { listener?.selectCurrentSong(position) }
    }

    override fun getItemCount(): Int {
        return nameList?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSongsList(list: List<Song>?) {
        nameList?.clear()
        if (list != null) {
            nameList?.addAll(list)
        }
        notifyDataSetChanged()
    }
}