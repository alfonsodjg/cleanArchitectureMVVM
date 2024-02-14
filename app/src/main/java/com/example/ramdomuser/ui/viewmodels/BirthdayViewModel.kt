package com.example.ramdomuser.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ramdomuser.data.local.datamodel.BirthdayDataModelRealm
import com.example.ramdomuser.data.local.datamodel.EmailDataModelRealm
import com.example.ramdomuser.domain.usecase.GetRandomUserLocalUseCase
import com.example.ramdomuser.domain.usecase.GetUserRandomDomainUseCase
import com.example.ramdomuser.ui.models.RegisteredView
import com.example.ramdomuser.ui.viewstate.BirthdayViewState
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
class BirthdayViewModel @Inject constructor(
    private var userCase:GetUserRandomDomainUseCase,
    private var useCaseUserProvider:GetRandomUserLocalUseCase
) : ViewModel() {
    var viewState = MutableLiveData<BirthdayViewState>()

    private val config = RealmConfiguration.create(schema = setOf(BirthdayDataModelRealm::class))
    private val realm: Realm =  Realm.open(config)

    init {
        viewState.value= BirthdayViewState()
    }

    fun onNewUser(){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    val result = userCase()
                    println("Resultado local en email: $result")
                    viewState.postValue(viewState.value?.copy(isLoading = true))
                    if (result != null){
                        viewState.postValue(
                            viewState.value?.copy(
                                isLoading = false,
                                userRandom = result.map {
                                    RegisteredView(
                                        date = it.registered.date,
                                        picture = it.picture,
                                        location = it.location
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

    fun userProvider(){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    val result = useCaseUserProvider()
                    println("Resultado local en email: ${result}")
                    viewState.postValue(viewState.value?.copy(isLoading = true))


                    if (result != null){
                        viewState.postValue(
                            viewState.value?.copy(
                                isLoading = false,
                                userRandom = listOf(
                                    RegisteredView(
                                        date = result.registered.date,
                                        picture = result.picture,
                                        location = result.location
                                    )
                                )
                            )
                        )
                    }
                }catch (e: IOException){
                    println("Error $e")
                }
            }
        }
    }

    fun onSaveData(){
        val result = useCaseUserProvider()
        realm.writeBlocking {
            copyToRealm(BirthdayDataModelRealm().apply {
                id = UUID.randomUUID().toString()
                date = result!!.registered.date
                picture = result.picture.thumbnail
                city = result.location.city
                state = result.location.state
                country = result.location.country
                postcode = result.location.postcode
            })
        }
    }

    fun showData(){
        val items:  RealmResults<BirthdayDataModelRealm> = realm.query<BirthdayDataModelRealm>().find()
        for (item  in  items){
            val addressUser = "${item.city}, ${item.state}, ${item.country}, ${item.postcode}"
            println("id: ${item.id} cumpleaños: ${item.date} direccion:$addressUser foto: ${item.picture}")
        }
    }

}