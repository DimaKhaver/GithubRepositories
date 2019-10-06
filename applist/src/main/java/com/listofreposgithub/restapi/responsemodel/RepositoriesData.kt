package com.listofreposgithub.restapi.responsemodel

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize data class RepositoriesData(
    @field:Json(name = "total_count") val totalCount: Int?,
    @field:Json(name = "incomplete_results") val incompleteResults: Boolean?,
    @field:Json(name = "items") val itemsList: List<ItemsData>

) : Parcelable

@Parcelize data class ItemsData(
    @field:Json(name = "login") val login: String,
    @field:Json(name = "avatar_url") val avatar: String,
    @field:Json(name = "id") val id: Int

): Parcelable

