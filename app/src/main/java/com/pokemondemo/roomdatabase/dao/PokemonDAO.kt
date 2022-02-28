package com.pokemondemo.roomdatabase.dao

import androidx.room.*
import com.pokemondemo.roomdatabase.entity.PokemonEntity

@Dao
interface PokemonDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListPokemon(pokemonEntities: ArrayList<PokemonEntity>?)

    @Query("SELECT * FROM PokemonEntity LIMIT :limit OFFSET :offset")
    suspend fun getListPokemon(offset: Int = 0, limit: Int = 50): List<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity WHERE name LIKE '%' || :name || '%'")
    suspend fun searchPokemon(name: String): List<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity WHERE name LIKE '%' || :name || '%' AND isFavorite = 1")
    suspend fun searchFavoritePokemon(name: String): List<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity WHERE isFavorite = 1 LIMIT :limit OFFSET :offset")
    suspend fun getFavoriteListPokemon(offset: Int = 0, limit: Int = 50): List<PokemonEntity>

    @Update
    suspend fun updatePokemon(pokemonEntity: PokemonEntity?)
}