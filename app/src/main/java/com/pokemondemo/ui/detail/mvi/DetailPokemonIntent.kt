package com.pokemondemo.ui.detail.mvi

import com.pokemondemo.api.model.Pokemon

sealed class DetailPokemonIntent {
    class FetchData(val pokemon: Pokemon) : DetailPokemonIntent()
}
