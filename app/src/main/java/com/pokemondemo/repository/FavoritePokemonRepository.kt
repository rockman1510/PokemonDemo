package com.pokemondemo.repository

import com.pokemondemo.roomdatabase.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface FavoritePokemonRepository {
    suspend fun getLocalFavoritePokemonList(offset: Int, limit: Int = 50): Flow<List<PokemonEntity>>
    suspend fun searchFavoritePokemon(name: String): Flow<List<PokemonEntity>>
}