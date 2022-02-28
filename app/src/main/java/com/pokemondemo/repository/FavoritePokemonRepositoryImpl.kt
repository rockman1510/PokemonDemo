package com.pokemondemo.repository

import com.pokemondemo.base.CoroutinesDispatcherProvider
import com.pokemondemo.roomdatabase.AppDataBase
import com.pokemondemo.roomdatabase.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavoritePokemonRepositoryImpl @Inject constructor(
    private val appDataBase: AppDataBase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : FavoritePokemonRepository {
    override suspend fun getLocalFavoritePokemonList(
        offset: Int,
        limit: Int
    ): Flow<List<PokemonEntity>> {
        return flow {
            emit(appDataBase.getPokemonDAO().getFavoriteListPokemon(offset, limit))
        }.flowOn(dispatcherProvider.IO)
    }

    override suspend fun searchFavoritePokemon(name: String): Flow<List<PokemonEntity>> {
        return flow {
            emit(appDataBase.getPokemonDAO().searchFavoritePokemon(name))
        }.flowOn(dispatcherProvider.IO)
    }
}