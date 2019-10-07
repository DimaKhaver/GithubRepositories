package com.listofreposgithub.domain


data class UserInfoModel constructor(
    val totalCount: Int,
    val login: String,
    val avatar: String,
    val id: String
)
