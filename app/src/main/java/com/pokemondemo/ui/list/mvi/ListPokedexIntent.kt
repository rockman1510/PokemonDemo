package com.pokemondemo.ui.list.mvi

import com.pokemondemo.api.model.Pokemon

sealed class ListPokedexIntent {
    class FetchData(val offset: Int, val limit: Int = 50) : ListPokedexIntent()
    class SearchPokedex(val name: String): ListPokedexIntent()
    class UpdatePokedex(val pokemon: Pokemon, val isFavorite: Boolean) : ListPokedexIntent()
    object SaveSuccessState : ListPokedexIntent()
}
