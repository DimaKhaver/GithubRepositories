package com.listofreposgithub.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.listofreposgithub.repository.UserDataRepository
import com.listofreposgithub.restapi.responsemodel.RepositoriesData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ListOfReposViewModel(application: Application) : BaseViewModel<RepositoriesData>(application) {

    private val userDataRepository = UserDataRepository()
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    override fun getDataFromInternet() {
        viewModelScope.launch {
            userDataRepository.loadData()
        }
    }

    override fun saveToDatabase(data: RepositoriesData) {

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
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
}
