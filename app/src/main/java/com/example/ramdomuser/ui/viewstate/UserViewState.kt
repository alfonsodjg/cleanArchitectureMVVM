package com.example.ramdomuser.ui.viewstate

import com.example.ramdomuser.ui.models.UserView

data class UserViewState(
    var isLoading: Boolean = false,
    var userRandom: List<UserView> = emptyList(),
    var error: String?=null
)
