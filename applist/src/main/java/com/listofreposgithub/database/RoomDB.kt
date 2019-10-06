package com.listofreposgithub.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.listofreposgithub.domain.UserInfoModel

@Dao
interface UserDao {
    @Query("select * from userinfo")
    fun getUsers(): LiveData<List<UserInfo?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserInfo?>)
}

@Entity
data class UserInfo constructor(
    @PrimaryKey
    val totalCount: String,
    val login: String?,
    val avatar: String?,
    val id: String?
)

fun List<UserInfo?>.asDomainModel(): List<UserInfoModel> {
    return map {
        UserInfoModel(
            totalCount = it?.totalCount,
            login = it?.login,
            avatar = it?.avatar,
            id = it?.id
        )
    }
}

@Database(entities = [UserInfo::class], version = 1)
abstract class UsersDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}

private lateinit var INSTANCE: UsersDatabase

fun getUsersDatabase(context: Context): UsersDatabase = synchronized(UsersDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java, "videos").build()
        }
    return INSTANCE
}
