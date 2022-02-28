package com.pokemondemo.repository

import com.pokemondemo.api.ApiService
import com.pokemondemo.api.model.Pokedex
import com.pokemondemo.api.model.PokemonStat
import com.pokemondemo.api.request.PokemonQueryBuilder
import com.pokemondemo.base.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeRepositoryForRemoteImpl(
    private val apiService: ApiService,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) {
    suspend fun getListPokemonApi(query: PokemonQueryBuilder): Flow<ArrayList<Pokedex>> {
        return flow {
            val response = apiService.getPokemonListApi(query.build())
            response.body()?.results?.apply { emit(this) }
        }.flowOn(dispatcherProvider.IO)
    }

    suspend fun getPokemonApi(id: String?): Flow<ArrayList<PokemonStat>> {
        return flow {
            val response = apiService.getPokemonApi(id)
            response.body()?.stats?.apply { emit(this) }
        }.flowOn(dispatcherProvider.IO)
    }
}