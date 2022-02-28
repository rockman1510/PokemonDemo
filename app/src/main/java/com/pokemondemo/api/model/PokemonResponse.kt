package com.pokemondemo.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("stats")
    val stats: ArrayList<PokemonStat>?,
)