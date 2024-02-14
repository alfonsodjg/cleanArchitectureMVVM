package com.example.ramdomuser.data.local.datamodel

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class EmailDataModelRealm():RealmObject {
    @PrimaryKey
    var id: String =""
    var email:String =""
    var password: String =""
    var picture: String =""
}