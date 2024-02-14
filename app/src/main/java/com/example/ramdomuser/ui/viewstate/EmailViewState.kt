package com.example.ramdomuser.ui.viewstate

import com.example.ramdomuser.ui.models.ResultView

 data class EmailViewState(
     var isLoading: Boolean = false,
     var userRandom: List<ResultView> = emptyList(),
     var error: String?=null
 )


