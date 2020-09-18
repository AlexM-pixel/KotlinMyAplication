package com.example.kotlinmyaplicationtasks.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinmyaplicationtasks.model.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User):Long

    @Insert
     fun insertUser(vararg user: User)

    @Query("SELECT * from user")
    fun getAllUsers(): LiveData<MutableList<User>>

    @Query("SELECT * from user WHERE id=:id")
     fun getById(id: Long): User

    @Query("DELETE from user")
     fun deleteAll()
}