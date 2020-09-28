package com.example.kotlinmyaplicationtasks.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmyaplicationtasks.R
import com.example.kotlinmyaplicationtasks.databinding.ItemForRecyclerBinding
import com.example.kotlinmyaplicationtasks.model.User

class UserAdapter() :
    RecyclerView.Adapter<UserAdapter.MyHolder>() {
    private  var usersList: MutableList<User> = mutableListOf()
    class MyHolder(val binding: ItemForRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener        // вот здесь к чему эта аннотация не могу понять
                }
               Toast.makeText(binding.root.context, binding.user?.name,Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setUserList(list: List<User>) {
        usersList.clear()
        usersList.addAll(list)
              notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflaterMy = LayoutInflater.from(parent.context)
        val binding = ItemForRecyclerBinding.inflate(inflaterMy)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(usersList.get(position))

    }
}