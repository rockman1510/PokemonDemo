package com.pokemondemo.ui.list.mvi

import com.pokemondemo.api.model.Pokemon

sealed class ListPokemonState {
    object OnLoading : ListPokemonState()
    class OnSuccess(val dataList: ArrayList<Pokemon>) : ListPokemonState()
    class OnError(val message: String) : ListPokemonState()
}