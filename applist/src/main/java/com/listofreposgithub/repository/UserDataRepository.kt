package com.listofreposgithub.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.listofreposgithub.database.UsersDatabase
import com.listofreposgithub.database.asDomainModel
import com.listofreposgithub.domain.UserInfoModel
import com.listofreposgithub.restapi.RestClient
import com.listofreposgithub.restapi.responsemodel.asDatabaseModel
import com.listofreposgithub.restapi.responsemodel.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserDataRepository(private val usersDB: UsersDatabase) {

    private val filterParam: String = "repos:1"
    private val pageParam: String = "1"
    private val perPageValParam: String = "20"

    private var _loadResponse = MutableLiveData<List<UserInfoModel>>()

    val loadResponse: LiveData<List<UserInfoModel>>
        get() = _loadResponse

    suspend fun loadData(isNetworkAvailable: Boolean) {
        when (isNetworkAvailable) {
            true -> loadDataFromInternet()
            false -> getDataFromDB()
        }
    }

    private suspend fun getDataFromDB() {
        withContext(Dispatchers.IO) {
            val users = usersDB.userDao.getUsers()
            _loadResponse.postValue(users.asDomainModel())
        }
    }

    private suspend fun loadDataFromInternet() {
        withContext(Dispatchers.IO) {
            Timber.d("repository: loadData() is called")
            val api = RestClient.getClient
            val repositoriesData = api.getUserList(filterParam, pageParam, perPageValParam).await()
            usersDB.userDao.insertAll(repositoriesData.asDatabaseModel())
            _loadResponse.postValue(repositoriesData.asDomainModel())
        }
    }
}
