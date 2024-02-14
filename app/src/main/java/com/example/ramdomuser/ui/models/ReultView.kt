package com.example.ramdomuser.ui.models

import com.example.ramdomuser.data.models.LoginModelData
import com.example.ramdomuser.data.models.PictureModelData

data class ResultView(
    val email: String,
    val login: LoginModelData,
    val picture: PictureModelData,
)