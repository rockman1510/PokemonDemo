package com.pokemondemo.api.request

import com.pokemondemo.constant.Constants


class ListPokemonQueryBuilder : PokemonQueryBuilder() {
    private var offset: Int? = 0
    private var limit: Int? = 50

    fun setOffset(offset: Int = 0): ListPokemonQueryBuilder {
        this.offset = offset
        return this
    }

    fun setLimit(limit: Int = 50): ListPokemonQueryBuilder {
        this.limit = limit
        return this
    }

    override fun putQueryParams(queryParams: MutableMap<String, String>) {
        offset?.apply { queryParams[Constants.QUERY_OFFSET] = "$this" }
        limit?.apply { queryParams[Constants.QUERY_LIMIT] = "$this" }
    }
}
