package com.example.ramdomuser.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ramdomuser.data.local.datamodel.UserDataModelRealm
import com.example.ramdomuser.domain.usecase.GetRandomUserLocalUseCase
import com.example.ramdomuser.domain.usecase.GetUserRandomDomainUseCase
import com.example.ramdomuser.ui.models.UserView
import com.example.ramdomuser.ui.viewstate.UserViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private var userCase:GetUserRandomDomainUseCase,
    private var useCaseUserProvider: GetRandomUserLocalUseCase
) : ViewModel() {
    var viewState = MutableLiveData<UserViewState>()

    private val config = RealmConfiguration.create(schema = setOf(UserDataModelRealm::class))
    private val realm: Realm = Realm.open(config)

    init {
        viewState.value= UserViewState()
    }

    fun onNewUser(){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    viewState.postValue(viewState.value?.copy(isLoading = true))
                    val result = userCase()
                    println("resultado en oncreatemodel: ${result}")

                    if (result.isNotEmpty()){
                        viewState.postValue(
                            viewState.value?.copy(
                                isLoading = false,
                                userRandom = result.map {
                                    UserView(
                                        name = it.name,
                                        picture = it.picture,
                                        phone = it.phone
                                    )
                                }
                            )
                        )
                    }
                }catch (e: IOException){
                    println("Error $e")
                }
            }
        }
    }
    fun onCreate(){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    val result = userCase()
                    viewState.postValue(viewState.value?.copy(isLoading = true))
                    if(result.isNotEmpty()){
                        viewState.postValue(viewState.value?.copy(
                            isLoading = false,
                            userRandom = result.map
                            {
                                UserView(
                                    name = it.name,
                                    phone = it.phone,
                                    picture = it.picture
                                )
                            }
                        ))
                    }
                }catch (e: IOException){
                    println("error try catch $e")
                }
            }
        }
    }

    fun onSaveDatas(){
        val result = useCaseUserProvider()
        realm.writeBlocking {
            copyToRealm(UserDataModelRealm().apply {
                id = UUID.randomUUID().toString()
                name= result!!.name.first
                phone = result.phone
                picture = result.picture.thumbnail
            })
        }
    }
    fun verDatas(){
        // all items in the realm
        val items: RealmResults<UserDataModelRealm> = realm.query<UserDataModelRealm>().find()
        for (item in items){
            println("id: ${item.id} name: ${item.name} phone: ${item.phone} foto: ${item.picture}")
        }
    }
}