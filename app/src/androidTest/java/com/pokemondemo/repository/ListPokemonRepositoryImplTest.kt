package com.pokemondemo.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pokemondemo.api.model.Pokedex
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.api.request.ListPokemonQueryBuilder
import com.pokemondemo.utils.PokemonUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ListPokemonRepositoryImplTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var listPokemonRepository: ListPokemonRepository


    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun getListPokemonApi20() = runBlocking {
        var dataList = arrayListOf<Pokedex>()
        val queryBuilder =
            ListPokemonQueryBuilder().setOffset(100).setLimit(20)
        listPokemonRepository.getListPokemonApi(queryBuilder).collect {
            dataList = it
        }
        assertNotNull(dataList)
        assertEquals(dataList[0].url, "https://pokeapi.co/api/v2/pokemon/101/")
        assertEquals(dataList[dataList.size - 1].url, "https://pokeapi.co/api/v2/pokemon/120/")
        assertEquals(dataList.size, 20)
    }

    @Test
    fun getListPokemonApi50() = runBlocking {
        var dataList = arrayListOf<Pokedex>()
        val queryBuilder =
            ListPokemonQueryBuilder().setOffset(0).setLimit(50)
        listPokemonRepository.getListPokemonApi(queryBuilder).collect {
            dataList = it
        }
        assertNotNull(dataList)
        assertEquals(dataList[0].url, "https://pokeapi.co/api/v2/pokemon/1/")
        assertEquals(dataList[dataList.size - 1].url, "https://pokeapi.co/api/v2/pokemon/50/")
        assertEquals(dataList.size, 50)
    }


    @Test
    fun insertLocalListPokemon() = runBlocking {
        var dataList = arrayListOf<Pokemon>()
        val queryBuilder =
            ListPokemonQueryBuilder().setOffset(0).setLimit(50)
        listPokemonRepository.getListPokemonApi(queryBuilder).collect {
            dataList = PokemonUtils.fromResponseToPokemonModel(it)
        }
        listPokemonRepository.insertLocalListPokemon(
            PokemonUtils.convertToPokemonEntities(dataList)
        )
        var pokemonList = arrayListOf<Pokemon>()
        listPokemonRepository.getLocalListPokemon(0, 50).collect{
            pokemonList = PokemonUtils.convertToPokemonModels(ArrayList(it))
        }
        assertNotNull(dataList)
        assertNotNull(pokemonList)
        assertEquals(pokemonList.size, 50)
    }


    // Run after insertLocalListPokemon() function
    @Test
    fun getLocalListPokemon() = runBlocking {
        var pokemonList = arrayListOf<Pokemon>()
        listPokemonRepository.getLocalListPokemon(0, 50).collect{
            pokemonList = PokemonUtils.convertToPokemonModels(ArrayList(it))
        }
        assertNotNull(pokemonList)
        assertEquals(pokemonList.size, 50)
    }

    // Run after insertLocalListPokemon() function
    @Test
    fun searchLocalPokemon() = runBlocking {
        var searchList = arrayListOf<Pokemon>()
        listPokemonRepository.searchPokemon("bu").collect{
            searchList = PokemonUtils.convertToPokemonModels(ArrayList(it))
        }
        assertNotNull(searchList)
        assert(searchList.size > 0)
    }

    // Run after insertLocalListPokemon() function
    @Test
    fun updateLocalPokemon() = runBlocking {
        var pokemonList = arrayListOf<Pokemon>()
        listPokemonRepository.getLocalListPokemon(0, 1).collect{
            pokemonList = PokemonUtils.convertToPokemonModels(ArrayList(it))
        }
        assertNotNull(pokemonList)
        assertEquals(pokemonList.size, 1)
        val isFavorite = pokemonList[0].isFavorite
        val newPokemon = PokemonUtils.updatePokemon(pokemonList[0], !isFavorite!!)
        listPokemonRepository.updateLocalPokemon(PokemonUtils.fromModelToEntity(newPokemon))

        listPokemonRepository.getLocalListPokemon(0, 1).collect{
            pokemonList = PokemonUtils.convertToPokemonModels(ArrayList(it))
        }
        assertEquals(pokemonList[0].isFavorite, !isFavorite)
    }

}