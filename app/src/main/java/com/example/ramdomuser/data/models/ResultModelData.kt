package com.example.ramdomuser.data.models

data class ResultModelData(
    val email: String,
    val location: LocationModelData,
    val login: LoginModelData,
    val name: NameModelData,
    val phone: String,
    val picture: PictureModelData,
    val registered: RegisteredModelData
)