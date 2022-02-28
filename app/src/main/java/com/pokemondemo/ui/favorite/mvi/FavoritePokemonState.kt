package com.pokemondemo.ui.favorite.mvi

import com.pokemondemo.api.model.Pokemon

sealed class FavoritePokemonState {
    object OnLoading : FavoritePokemonState()
    class OnSuccess(val dataList: ArrayList<Pokemon>) : FavoritePokemonState()
    class OnError(val message: String) : FavoritePokemonState()
}