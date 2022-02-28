package com.pokemondemo.repository

import com.pokemondemo.api.model.PokemonStat
import com.pokemondemo.roomdatabase.entity.PokemonStatEntity
import kotlinx.coroutines.flow.Flow

interface DetailPokemonRepository {

    suspend fun getPokemonApi(id: String?): Flow<ArrayList<PokemonStat>>

    suspend fun getLocalPokemonStat(id: String?): Flow<PokemonStatEntity>

    suspend fun insertLocalPokemonStat(pokemonStatEntity: PokemonStatEntity?)
}