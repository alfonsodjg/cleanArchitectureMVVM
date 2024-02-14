package com.example.ramdomuser.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ramdomuser.data.local.datamodel.EmailDataModelRealm
import com.example.ramdomuser.domain.usecase.GetRandomUserLocalUseCase
import com.example.ramdomuser.domain.usecase.GetUserRandomDomainUseCase
import com.example.ramdomuser.ui.models.ResultView
import com.example.ramdomuser.ui.viewstate.EmailViewState
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
class EmailViewModel @Inject constructor(
    private var userCase:GetUserRandomDomainUseCase,
    private var useCaseUserProvider: GetRandomUserLocalUseCase
) : ViewModel() {
    var viewState = MutableLiveData<EmailViewState>()

    private val config = RealmConfiguration.create(schema = setOf(EmailDataModelRealm::class))
    private val realm: Realm = Realm.open(config)

    init {
        viewState.value= EmailViewState()
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
                                    ResultView(
                                        email = it.email,
                                        picture = it.picture,
                                        login = it.login
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
    fun onUserProvider(){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    val result = useCaseUserProvider()
                    println("Resultado local en email: ${result}")
                    viewState.postValue(viewState.value?.copy(isLoading = true))
                    println("Enail viewmodel: ${result?.email} login: ${result?.login?.password} foto: ${result?.picture?.thumbnail}")
                    if(result != null){
                        viewState.postValue(viewState.value?.copy(
                            isLoading = false,
                            userRandom = listOf(
                                ResultView(
                                    email = result.email,
                                    login = result.login,
                                    picture = result.picture
                                )
                            )
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
            copyToRealm(EmailDataModelRealm().apply {
                id = UUID.randomUUID().toString()
                email= result!!.email
                password = result.login.password
                picture = result.picture.thumbnail
            })
        }
    }
    fun showDatas(){
        val items: RealmResults<EmailDataModelRealm> = realm.query<EmailDataModelRealm>().find()
        for (item in items){
            println("id: ${item.id} email: ${item.email} contraseña: ${item.password} foto: ${item.picture}")
        }
    }
}