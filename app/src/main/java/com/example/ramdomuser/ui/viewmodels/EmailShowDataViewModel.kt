package com.example.ramdomuser.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ramdomuser.data.local.datamodel.BirthdayDataModelRealm
import com.example.ramdomuser.data.local.datamodel.EmailDataModelRealm
import com.example.ramdomuser.data.local.datamodel.UserDataModelRealm
import com.example.ramdomuser.ui.viewstate.RealmDatasViewState
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class EmailShowDataViewModel: ViewModel() {
    var vieState = MutableLiveData<RealmDatasViewState>()

    init {
        vieState.value = RealmDatasViewState()
    }

    fun onShowDatasEmail(){
        val config = RealmConfiguration.create(schema = setOf(EmailDataModelRealm::class))
        val realm: Realm = Realm.open(config)
        vieState.value?.listDatasEmail = realm.query<EmailDataModelRealm>().find()
    }

    fun onShowDatasUser(){
        val config = RealmConfiguration.create(schema = setOf(UserDataModelRealm::class))
        val realm: Realm = Realm.open(config)
        vieState.value?.listDatasUser = realm.query<UserDataModelRealm>().find()
    }

    fun onShowDataBirthday(){
        val config = RealmConfiguration.create(schema = setOf(BirthdayDataModelRealm::class))
        val realm: Realm = Realm.open(config)
        vieState.value?.listDataBirthday = realm.query<BirthdayDataModelRealm>().find()
    }
}