package com.ufc.appdemo.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class HomeViewModel(application: Application): AndroidViewModel(application) {

    //TODO - O PARAMETRO DA LIST DEVE SER ALTERADO PARA A MODEL A SER CRIADA NO FUTURO
    var stateList: MutableLiveData<List<String>> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var stateFavorite: MutableLiveData<String> = MutableLiveData()

    private fun errorMessage(message: String) {
        error.value = message
    }
}