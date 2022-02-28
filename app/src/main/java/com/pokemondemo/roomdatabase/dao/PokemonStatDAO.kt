package com.pokemondemo.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemondemo.roomdatabase.entity.PokemonStatEntity

@Dao
interface PokemonStatDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: PokemonStatEntity?)

    @Query("SELECT * FROM PokemonStatEntity WHERE id = :id")
    suspend fun getPokemonStat(id: Long?): PokemonStatEntity

    @Query("DELETE FROM PokemonStatEntity")
    suspend fun deleteAll(): Int

}