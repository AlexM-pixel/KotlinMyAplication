package com.example.kotlinmyaplicationtasks.model.dao

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinmyaplicationtasks.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [User::class], version = 5)
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
                            .addMigrations(MIGRATION_1_2)
                            .build()
                    }
                }
            }

            return INSTANCE!!
        }

        private val MIGRATION_1_2 : Migration= object :Migration(2,5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user ADD COLUMN gender TEXT")
            }

        }

        suspend fun refactorDb(database: UsersDatabase?) {
            database?.let { db ->
                withContext(Dispatchers.IO) {
                    Log.e("Database", "refactorDb")
                    val userDao: UserDao = database.userDao()
                    userDao.deleteAll()

                    val userOne = User(null, name = "Kolya", age = 80, gender = "male")
                    val user2 = User(null, name = "Katya", age = 8,gender = "female")
                    val user3 = User(null, name = "Sara", age = 30,gender = "female")
                    val user4 = User(null, name = "Jon", age = 20,gender = "male")
                    userDao.insertUser(userOne, user2, user3, user4)
                }
            }
        }
    }
}