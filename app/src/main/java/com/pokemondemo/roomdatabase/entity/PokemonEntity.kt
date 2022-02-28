package com.pokemondemo.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PokemonEntity")
class PokemonEntity() {

    @PrimaryKey
    var id: Long? = null
    var name: String? = ""
    var url: String? = ""
    var imageUrl: String? = ""
    var isFavorite: Boolean? = false

    constructor(
        id: Long?,
        name: String?,
        url: String?,
        imageUrl: String?,
        isFavorite: Boolean? = false
    ) : this() {
        this.id = id
        this.name = name
        this.url = url
        this.imageUrl = imageUrl
        this.isFavorite = isFavorite
    }
}