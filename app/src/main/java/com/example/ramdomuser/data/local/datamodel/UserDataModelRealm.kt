package com.example.ramdomuser.data.local.datamodel

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class UserDataModelRealm :RealmObject{
    @PrimaryKey
    var id: String =""
    var name:String =""
    var phone: String =""
    var picture: String =""
}