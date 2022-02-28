package com.pokemondemo.repository

import com.pokemondemo.api.model.Pokedex
import com.pokemondemo.api.request.PokemonQueryBuilder
import com.pokemondemo.roomdatabase.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface ListPokemonRepository {
    suspend fun getListPokemonApi(query: PokemonQueryBuilder): Flow<ArrayList<Pokedex>>

    suspend fun getLocalListPokemon(offset: Int, limit: Int = 50): Flow<List<PokemonEntity>>

    suspend fun searchPokemon(name: String): Flow<List<PokemonEntity>>

    suspend fun insertLocalListPokemon(pokemonEntities: ArrayList<PokemonEntity>?)

    suspend fun updateLocalPokemon(pokemonEntity: PokemonEntity?)
}