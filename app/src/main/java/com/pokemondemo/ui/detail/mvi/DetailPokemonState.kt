package com.pokemondemo.ui.detail.mvi

import com.pokemondemo.api.model.Pokemon

sealed class DetailPokemonState {
    object OnLoading : DetailPokemonState()
    class OnSuccess(val pokemon: Pokemon) : DetailPokemonState()
    class OnError(val message: String) : DetailPokemonState()
}