package com.example.ramdomuser.data.local.datamodel

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class BirthdayDataModelRealm():RealmObject {
    @PrimaryKey
    var id: String = ""
    var date: String = ""
    var picture: String  = ""
    var city: String= ""
    var state: String= ""
    var country: String= ""
    var postcode: String= ""
}