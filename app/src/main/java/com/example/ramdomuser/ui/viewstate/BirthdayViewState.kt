package com.example.ramdomuser.ui.viewstate
import com.example.ramdomuser.ui.models.RegisteredView

data class BirthdayViewState(
    var isLoading: Boolean = false,
    var userRandom: List<RegisteredView> = emptyList(),
    var error: String?=null
)
