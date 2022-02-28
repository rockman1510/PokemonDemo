package com.pokemondemo.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pokemondemo.api.model.PokemonStat

@Entity(tableName = "PokemonStatEntity")
class PokemonStatEntity() {

    @PrimaryKey
    var id: Long? = null
    var stats: ArrayList<PokemonStat>? = null

    constructor(id: Long?, pokemonStats: ArrayList<PokemonStat>?) : this() {
        this.id = id
        this.stats = pokemonStats
    }
}