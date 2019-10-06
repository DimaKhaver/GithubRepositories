package com.listofreposgithub.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.listofreposgithub.database.UserInfo
import com.listofreposgithub.database.UsersDatabase
import com.listofreposgithub.database.asDomainModel
import com.listofreposgithub.domain.UserInfoModel
import com.listofreposgithub.restapi.RestClient
import com.listofreposgithub.restapi.responsemodel.RepositoriesDataContainer
import com.listofreposgithub.restapi.responsemodel.asDatabaseModel
import com.listofreposgithub.restapi.responsemodel.asDomainModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

class UserDataRepository(private val usersDB: UsersDatabase) {

    private val filterParam: String = "repos:1"
    private val pageParam: String = "1"
    private val perPageValParam: String = "20"

    private var _loadResponse = MutableLiveData<List<UserInfoModel>>()

    private val loadResponse: LiveData<List<UserInfoModel>>
        get() = _loadResponse

    suspend fun loadData(isNetworkAvailable: Boolean): LiveData<List<UserInfoModel>> {
        when (isNetworkAvailable) {
            true -> loadDataFromInternet()
            false -> checkDB(usersDB.userDao.getUsers())
        }
        return loadResponse
    }

    private suspend fun checkDB(users: LiveData<List<UserInfo?>>) = when (users.value.isNullOrEmpty()) {
        true -> _loadResponse.postValue(emptyList())
        else -> withContext(Dispatchers.IO) { getDataFromDB(users) }
    }

    fun saveDataToDB(repositoriesData: RepositoriesDataContainer)  {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                usersDB.userDao.insertAll(repositoriesData.asDatabaseModel())
            }
        }
    }

    private fun getDataFromDB(users: LiveData<List<UserInfo?>>) = Transformations.map(users) {
        it.asDomainModel()
    }

    private suspend fun loadDataFromInternet() {
        withContext(Dispatchers.IO) {
            try {
                Timber.d("repository: loadData() is called")
                val api = RestClient.getClient
                api.getUserList(filterParam, pageParam, perPageValParam)
                    .enqueue(object : Callback<RepositoriesDataContainer> {
                        override fun onFailure(
                            call: Call<RepositoriesDataContainer>,
                            t: Throwable
                        ) {
                            Timber.d("network request failed: ${t.message}")
                        }

                        override fun onResponse(
                            call: Call<RepositoriesDataContainer>,
                            response: Response<RepositoriesDataContainer>
                        ) {
                            Timber.d("network response successful: ${response.message()}")
                            response.body()?.let {
                                saveDataToDB(it)
                                _loadResponse.postValue(it.asDomainModel())
                            }
                        }
                    })
            } catch (e: HttpException) {
                Timber.d("Error: ${e.message()}")
            } catch (e: Throwable) {
                Timber.d("Error: ${e.message}")
            }
        }
    }
}
