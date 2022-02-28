package com.pokemondemo.api

import com.pokemondemo.api.model.PokemonResponse
import com.pokemondemo.api.model.ListPokedex
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {
    @GET("pokemon/")
    suspend fun getPokemonListApi(@QueryMap query: Map<String, String>): Response<ListPokedex>

    @GET("pokemon/{id}")
    suspend fun getPokemonApi(@Path("id") id: String?): Response<PokemonResponse>
}