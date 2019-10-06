package com.listofreposgithub.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.*
import com.listofreposgithub.database.getUsersDatabase
import com.listofreposgithub.domain.UserInfoModel
import com.listofreposgithub.repository.UserDataRepository
import com.listofreposgithub.restapi.responsemodel.RepositoriesDataContainer
import kotlinx.coroutines.*

class ListOfReposViewModel(application: Application) : BaseViewModel<RepositoriesDataContainer>(application) {

    private val userDataRepository = UserDataRepository(getUsersDatabase(application))
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val repositoryData: LiveData<List<UserInfoModel>>
        get() = _repositoriesData

    private var _repositoriesData = MutableLiveData<List<UserInfoModel>>()

    override fun setUpData(viewLifecycleOwner: LifecycleOwner) = viewModelScope.launch {
        userDataRepository.loadData(isNetworkAvailable(getApplication()))
            .observe(viewLifecycleOwner, Observer<List<UserInfoModel>> {
            _repositoriesData.value = it
        })
    }
/*
    override fun saveDataToDB() = viewModelScope.launch {
        userDataRepository.saveDataToDB()
    }
*/
    private fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (manager.activeNetworkInfo != null) manager.activeNetworkInfo.isConnectedOrConnecting else false
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListOfReposViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ListOfReposViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
