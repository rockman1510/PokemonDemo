package com.pokemondemo.utils

import com.pokemondemo.api.model.Pokedex
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.api.model.PokemonStat
import com.pokemondemo.roomdatabase.entity.PokemonEntity

object PokemonUtils {

    fun convertToPokemonModels(pokemonEntities: ArrayList<PokemonEntity>?): ArrayList<Pokemon> {
        val dataList = ArrayList<Pokemon>()
        pokemonEntities?.forEach {
            dataList.add(fromEntityToModel(it))
        }
        return dataList
    }

    fun fromResponseToPokemonModel(pokedexList: ArrayList<Pokedex>?): ArrayList<Pokemon> {
        val dataList = ArrayList<Pokemon>()
        pokedexList?.forEach {
            dataList.add(fromPokedexToPokemon(it))
        }
        return dataList
    }

    private fun fromPokedexToPokemon(pokedex: Pokedex): Pokemon {
        val id = StringUtils.pokemonUrlToId(pokedex.url).toString()
        return Pokemon(
            id, pokedex.name, pokedex.url, StringUtils.pokemonImageUrl(id.toInt()),
            arrayListOf(), false
        )
    }

    fun updatePokemon(pokemon: Pokemon, stats: ArrayList<PokemonStat>?): Pokemon {
        return Pokemon(
            pokemon.id, pokemon.name, pokemon.url, pokemon.imageUrl, stats, pokemon.isFavorite
        )
    }

    fun updatePokemon(pokemon: Pokemon, isFavorite: Boolean): Pokemon {
        return Pokemon(
            pokemon.id, pokemon.name, pokemon.url, pokemon.imageUrl, pokemon.stats, isFavorite
        )
    }

    fun convertToPokemonEntities(pokemonList: ArrayList<Pokemon>?): ArrayList<PokemonEntity> {
        val dataList = ArrayList<PokemonEntity>()
        pokemonList?.forEach {
            dataList.add(fromModelToEntity(it))
        }
        return dataList
    }

    private fun fromEntityToModel(pokemonEntity: PokemonEntity): Pokemon {
        return Pokemon(
            pokemonEntity.id.toString(),
            pokemonEntity.name,
            pokemonEntity.url,
            pokemonEntity.imageUrl,
            arrayListOf(),
            pokemonEntity.isFavorite
        )
    }

    fun fromModelToEntity(pokemon: Pokemon): PokemonEntity {
        return PokemonEntity(
            StringUtils.pokemonUrlToId(pokemon.url),
            pokemon.name,
            pokemon.url,
            pokemon.imageUrl,
            pokemon.isFavorite
        )
    }
}