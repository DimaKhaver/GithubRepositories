package com.listofreposgithub.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.listofreposgithub.database.utils.testUsers
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DBTest {

    private lateinit var database: UsersDatabase
    private lateinit var userDaoVal: UserDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, UsersDatabase::class.java).build()
        userDaoVal = database.userDao
        database.userDao.insertAll(testUsers)
    }

    @After fun closeDB() {
        database.close()
    }

    @Test fun testDataDB() = runBlocking {
        assertNotNull(database.userDao.getUsers())
        assertTrue(database.userDao.getUsers().isNotEmpty())
    }

}