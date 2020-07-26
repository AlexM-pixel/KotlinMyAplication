package com.example.kotlinmyaplicationtasks.task3

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmyaplicationtasks.R

class MyAdapterTask3(val listTime: MutableList<String>) :
    RecyclerView.Adapter<MyAdapterTask3.MyViewHolder>() {


    class MyViewHolder(v: CardView) : RecyclerView.ViewHolder(v) {
        val number: TextView = v.findViewById(R.id.text_view_N)
        val timeTextView: TextView = v.findViewById(R.id.time_show_cardVew)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: CardView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardforspisok, parent, false) as CardView
        return MyViewHolder(v)
    }

    override fun getItemCount() = listTime.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.number.text = position.toString()
        holder.timeTextView.text = listTime.get(position)
    }

    fun removeItem(position: Int, viewHolder: RecyclerView.ViewHolder) {
        listTime.removeAt(position)                                         // удалил из списка выбранный элемент
        notifyItemRemoved(position)                                        // обновил адаптер
    }
}