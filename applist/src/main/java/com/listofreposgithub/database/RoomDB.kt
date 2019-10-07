package com.listofreposgithub.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.*
import com.listofreposgithub.domain.UserInfoModel

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)
}

@Entity
data class User constructor(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val primaryKey: Int? = null,
    val totalCount: Int,
    val login: String,
    val avatar: String,
    val id: String
)

fun List<User>.asDomainModel(): List<UserInfoModel> {
    return map {
        UserInfoModel(
            totalCount = it.totalCount,
            login = it.login,
            avatar = it.avatar,
            id = it.id
        )
    }
}

@Database(entities = [User::class], version = 1)
abstract class UsersDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}

private lateinit var INSTANCE: UsersDatabase

fun getUsersDatabase(context: Context): UsersDatabase {
    synchronized(UsersDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                UsersDatabase::class.java, "users"
            ).build()
        }
    }
    return INSTANCE
}
