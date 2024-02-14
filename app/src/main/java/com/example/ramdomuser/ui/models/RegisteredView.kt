package com.example.ramdomuser.ui.models

import com.example.ramdomuser.data.models.LocationModelData
import com.example.ramdomuser.data.models.PictureModelData

data class RegisteredView(
    val date: String,
    val picture: PictureModelData,
    val location: LocationModelData
)