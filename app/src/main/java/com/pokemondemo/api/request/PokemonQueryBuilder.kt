package com.pokemondemo.api.request

abstract class PokemonQueryBuilder {

    fun build(): Map<String, String> {
        val queryParams = HashMap<String, String>()
        putQueryParams(queryParams)
        return queryParams
    }

    abstract fun putQueryParams(queryParams: MutableMap<String, String>)

}
