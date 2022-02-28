package com.pokemondemo.repository

import com.pokemondemo.api.ApiService
import com.pokemondemo.api.model.Pokedex
import com.pokemondemo.api.request.PokemonQueryBuilder
import com.pokemondemo.base.CoroutinesDispatcherProvider
import com.pokemondemo.roomdatabase.AppDataBase
import com.pokemondemo.roomdatabase.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListPokemonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDataBase: AppDataBase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ListPokemonRepository {

    override suspend fun getListPokemonApi(query: PokemonQueryBuilder): Flow<ArrayList<Pokedex>> {
        return flow {
            val response = apiService.getPokemonListApi(query.build())
            response.body()?.results?.apply { emit(this) }
        }.flowOn(dispatcherProvider.IO)
    }

    override suspend fun getLocalListPokemon(
        offset: Int, limit: Int
    ): Flow<List<PokemonEntity>> {
        return flow { emit(appDataBase.getPokemonDAO().getListPokemon(offset, limit)) }
            .flowOn(dispatcherProvider.IO)
    }

    override suspend fun searchPokemon(name: String): Flow<List<PokemonEntity>> {
        return flow { emit(appDataBase.getPokemonDAO().searchPokemon(name)) }.flowOn(
            dispatcherProvider.IO
        )
    }

    override suspend fun insertLocalListPokemon(pokemonEntities: ArrayList<PokemonEntity>?) {
        withContext(dispatcherProvider.IO) {
            appDataBase.getPokemonDAO().insertListPokemon(pokemonEntities)
        }
    }

    override suspend fun updateLocalPokemon(pokemonEntity: PokemonEntity?) {
        withContext(dispatcherProvider.IO) {
            appDataBase.getPokemonDAO().updatePokemon(pokemonEntity)
        }
    }
}