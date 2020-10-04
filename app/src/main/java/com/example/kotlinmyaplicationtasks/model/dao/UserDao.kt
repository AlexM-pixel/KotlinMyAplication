package com.example.kotlinmyaplicationtasks.model.dao

 import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinmyaplicationtasks.model.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User): Long

    @Insert
    fun insertUser(vararg user: User)

    @Query("SELECT * from user")
    fun getAllUsers(): LiveData<MutableList<User>>

    @Query("SELECT * from user WHERE id=:id")
    fun getById(id: Long): User

    @Query("SELECT * from user WHERE gender=:gender OR gender=:gender2")
    fun getByGender(gender: String,gender2:String): LiveData<MutableList<User>>

    @Query("SELECT * from user WHERE age>=:age")
    fun getByOld(age: Int): LiveData<MutableList<User>>

    @Query("DELETE from user")
    fun deleteAll()

    @Query("SELECT * from user WHERE (:age IS NULL OR age>= :age) AND (:gender IS NULL OR gender= :gender)")
   fun getAllSortUsers(age:Int?, gender:String?):LiveData<List<User>>
}