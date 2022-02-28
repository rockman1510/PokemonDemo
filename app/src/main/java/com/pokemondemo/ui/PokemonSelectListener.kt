package com.pokemondemo.ui

import com.pokemondemo.api.model.Pokemon

interface PokemonSelectListener {
    fun onPokemonSelected(pokemon: Pokemon)
}