package com.listofreposgithub.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel<T>(application: Application) : AndroidViewModel(application) {
    abstract fun getDataFromInternet()
    abstract fun saveToDatabase(data: T)
}