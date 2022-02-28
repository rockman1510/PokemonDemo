package com.pokemondemo.utils

object StringUtils {

    @JvmStatic
    fun pokemonUrlToId(url: String?): Long {
        var value = 0L
        url?.let {
            val subArr = it.substring(0, url.length - 1).split("/")
            if (!subArr.isNullOrEmpty()) {
                subArr.last().all { subString -> Character.isDigit(subString) }
                    .also { value = subArr.last().toLong() }
            }
        }
        return value
    }

    @JvmStatic
    fun pokemonStringId(id: String): String {
        if (id.length == 1) {
            return "#00$id"
        }
        if (id.length == 2) {
            return "#0$id"
        }
        return "#$id"
    }

    @JvmStatic
    fun pokemonStringName(name: String?): String {
        var value = ""
        name?.let {
            if (name.length > 1) {
                value = "${name[0].uppercase()}${name.substring(1)}"
            }
            if (name.length == 1) {
                value = name[0].uppercase()
            }
        }
        return value
    }

    @JvmStatic
    fun pokemonImageUrl(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }

}