package com.listofreposgithub.restapi.responsemodel

import com.listofreposgithub.database.UserInfo
import com.listofreposgithub.domain.UserInfoModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoriesDataContainer(
    @field:Json(name = "total_count") val totalCount: String,
  //@field:Json(name = "incomplete_results") val incompleteResults: Boolean?,
    @field:Json(name = "items") val itemsList: List<ItemsData?>
)

@JsonClass(generateAdapter = true)
data class ItemsData(
    @field:Json(name = "login") val login: String?,
    @field:Json(name = "avatar_url") val avatar: String?,
    @field:Json(name = "id") val id: String?
)

fun RepositoriesDataContainer.asDatabaseModel(): List<UserInfo> {
    return itemsList.map {
        UserInfo(
            totalCount = totalCount,
            login = it?.login,
            avatar = it?.avatar,
            id = it?.id
        )
    }
}

fun RepositoriesDataContainer.asDomainModel(): List<UserInfoModel> {
    return itemsList.map {
        UserInfoModel(
            totalCount = totalCount,
            login = it?.login,
            avatar = it?.avatar,
            id = it?.id
        )
    }
}