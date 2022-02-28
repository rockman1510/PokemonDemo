package com.pokemondemo.base.mvi

interface BaseMVIView<STATE> {
    fun onCallBackState(state: STATE)
}