package com.example.ramdomuser.domain.models

import com.example.ramdomuser.data.models.LocationModelData
import com.example.ramdomuser.data.models.LoginModelData
import com.example.ramdomuser.data.models.NameModelData
import com.example.ramdomuser.data.models.PictureModelData
import com.example.ramdomuser.data.models.RegisteredModelData
import com.example.ramdomuser.data.models.ResultModelData

data class ResulModelDomain(
    val email: String,
    val location: LocationModelData,
    val login: LoginModelData,
    val name: NameModelData,
    val phone: String,
    val picture: PictureModelData,
    val registered: RegisteredModelData
)

//fun List<ResultModelData>.toDomain()= ResulModelDomain(email,location,login,name,phone,picture,registered)
