package com.example.kotlinmyaplicationtasks.model.dao

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinmyaplicationtasks.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [User::class], version = 2)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE: UsersDatabase? = null
        private const val DB_NAME = "users.db"

        fun getDatabase(context: Context): UsersDatabase {
            if (INSTANCE == null) {
                synchronized(UsersDatabase::class.java) {
                    Log.e("Database", "instance==null")

                    if (INSTANCE == null) {
                        Log.e("Database", "instance==null22")
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            UsersDatabase::class.java,
                            DB_NAME
                        )
                            //  .allowMainThreadQueries() // раскомичу  если с этими долбаными корутинами не выйдет
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    Log.e("Database", "onCreateDb")
                                    super.onCreate(db)
                                    GlobalScope.launch(Dispatchers.IO) { refactorDb(INSTANCE) }
                                }
                            })
                            .build()
                    }
                }
            }

            return INSTANCE!!
        }


        suspend fun refactorDb(database: UsersDatabase?) {
            database?.let { db ->
                withContext(Dispatchers.IO) {
                    Log.e("Database", "refactorDb")
                    val userDao: UserDao = database.userDao()
                    userDao.deleteAll()

                    val userOne = User(null, name = "Kolya", age = 80)
                    val user2 = User(null, name = "Katya", age = 8)
                    val user3 = User(null, name = "Sara", age = 30)
                    val user4 = User(null, name = "Jon", age = 20)
                    userDao.insertUser(userOne, user2, user3, user4)
                }
            }
        }
    }
}