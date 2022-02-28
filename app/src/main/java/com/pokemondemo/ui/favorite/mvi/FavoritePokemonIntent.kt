package com.pokemondemo.ui.favorite.mvi

sealed class FavoritePokemonIntent {
    class FetchData(val offset: Int = 0, val limit: Int = 50) : FavoritePokemonIntent()
    class SearchPokemon(val name: String): FavoritePokemonIntent()
    object SaveSuccessState: FavoritePokemonIntent()
}
