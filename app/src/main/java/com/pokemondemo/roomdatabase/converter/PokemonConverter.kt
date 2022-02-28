package com.pokemondemo.roomdatabase.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pokemondemo.api.model.PokemonStat

class PokemonConverter {
    @TypeConverter
    fun fromCommitList(pokemonStats: ArrayList<PokemonStat>?): String {
        return Gson().toJson(pokemonStats).toString()
    }

    @TypeConverter
    fun toCommitList(string: String?): ArrayList<PokemonStat> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<PokemonStat>>() {}.type)
    }
}