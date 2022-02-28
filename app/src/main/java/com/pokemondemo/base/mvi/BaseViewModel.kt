package com.pokemondemo.base.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<INTENT, STATE> : ViewModel(),
    MVIViewModelContract<INTENT> {
    protected open val state: MutableLiveData<STATE> = MutableLiveData()
    fun getState(): LiveData<STATE> = state
}