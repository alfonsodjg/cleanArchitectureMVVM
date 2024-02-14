package com.example.ramdomuser.ui.viewstate

import com.example.ramdomuser.data.local.datamodel.BirthdayDataModelRealm
import com.example.ramdomuser.data.local.datamodel.EmailDataModelRealm
import com.example.ramdomuser.data.local.datamodel.UserDataModelRealm
import io.realm.kotlin.query.RealmResults

class RealmDatasViewState {
    var listDatasEmail: List<EmailDataModelRealm> = emptyList()
    var listDatasUser: List<UserDataModelRealm> = emptyList()
    var listDataBirthday: List<BirthdayDataModelRealm> = emptyList()
}