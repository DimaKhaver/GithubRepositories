package com.githubrepositories.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.listofreposgithub.viewmodels.ListOfReposViewModel

class DetailsViewModel : ViewModel() {

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
    }
}