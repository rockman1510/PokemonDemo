package com.pokemondemo.base.mvi

interface MVIViewModelContract<INTENT> {
    fun processIntent(intent: INTENT)
}