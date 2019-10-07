package com.listofreposgithub.restapi

import com.listofreposgithub.restapi.responsemodel.RepositoriesDataContainer
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/search/users")
    fun getUserList(@Query("q") filter: String,
                    @Query("page") pageNumber: String,
                    @Query("per_page") itemsPerPage: String): Deferred<RepositoriesDataContainer>

}