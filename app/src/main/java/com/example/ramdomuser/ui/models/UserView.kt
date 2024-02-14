package com.example.ramdomuser.ui.models

import com.example.ramdomuser.data.models.NameModelData
import com.example.ramdomuser.data.models.PictureModelData

data class UserView(
    val name: NameModelData,
    val phone: String,
    val picture: PictureModelData,
)