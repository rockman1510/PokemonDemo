package com.pokemondemo.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListPokedex(
    @Expose
    @SerializedName("count")
    val count: String,

    @Expose
    @SerializedName("next")
    val next: String,

    @Expose
    @SerializedName("previous")
    val previous: String,
    @Expose
    @SerializedName("results")
    val results: ArrayList<Pokedex>,
)

data class Pokedex(
    @Expose
    @SerializedName("name")
    val name: String?,
    @Expose
    @SerializedName("url")
    val url: String?,
)