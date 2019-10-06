package com.listofreposgithub.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Job

abstract class BaseViewModel<T>(application: Application) : AndroidViewModel(application) {
    abstract fun setUpData(viewLifecycleOwner: LifecycleOwner): Job
}