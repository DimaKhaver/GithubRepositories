package com.listofreposgithub.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.listofreposgithub.restapi.RestClient
import com.listofreposgithub.restapi.responsemodel.RepositoriesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class UserDataRepository {

    private val filterParam: String = "repos:1"
    private val pageParam: String = "1"
    private val perPageValParam: String = "20"

    private var _eventNetworkAccessibility = MutableLiveData<Boolean>(true)

    private val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkAccessibility

    suspend fun loadData() {
        val githubApi= RestClient.getClient
        withContext(Dispatchers.IO) {
            Timber.d("repository: loadData() is called")

            githubApi.getUserList(filterParam, pageParam, perPageValParam).enqueue(object : Callback<RepositoriesData> {
                override fun onFailure(call: Call<RepositoriesData>, t: Throwable) {
                    Timber.d("network request failed: ${t.message}")
                }

                override fun onResponse(call: Call<RepositoriesData>, response: Response<RepositoriesData>) {
                    Timber.d("network response successful: ${response.message()}")
                }
            })
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting
    }
}