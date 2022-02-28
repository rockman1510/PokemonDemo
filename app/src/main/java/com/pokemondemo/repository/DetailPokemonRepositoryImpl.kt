package com.pokemondemo.repository

import com.pokemondemo.api.ApiService
import com.pokemondemo.api.model.PokemonStat
import com.pokemondemo.base.CoroutinesDispatcherProvider
import com.pokemondemo.roomdatabase.AppDataBase
import com.pokemondemo.roomdatabase.entity.PokemonStatEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailPokemonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDataBase: AppDataBase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : DetailPokemonRepository {

    override suspend fun getPokemonApi(id: String?): Flow<ArrayList<PokemonStat>> {
        return flow {
            val response = apiService.getPokemonApi(id)
            response.body()?.stats?.apply { emit(this) }
        }.flowOn(dispatcherProvider.IO)
    }


    override suspend fun getLocalPokemonStat(id: String?): Flow<PokemonStatEntity> {
        return flow { emit(appDataBase.getPokemonStatDAO().getPokemonStat(id?.toLong())) }
            .flowOn(dispatcherProvider.IO)
    }

    override suspend fun insertLocalPokemonStat(pokemonStatEntity: PokemonStatEntity?) {
        withContext(dispatcherProvider.IO) {
            appDataBase.getPokemonStatDAO().insert(pokemonStatEntity)
        }
    }
}