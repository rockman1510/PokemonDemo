package com.pokemondemo.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pokemondemo.api.model.PokemonStat
import com.pokemondemo.roomdatabase.entity.PokemonStatEntity
import com.pokemondemo.utils.PokemonUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.ArrayList
import javax.inject.Inject

/*
Run this class after app loaded successfully and opening 3 first items in app (for local test methods)
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DetailPokemonRepositoryImplTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var detailPokemonRepository: DetailPokemonRepository

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun getPokemonApi1() = runBlocking {
        var pokemonStats = arrayListOf<PokemonStat>()
        detailPokemonRepository.getPokemonApi("1").collect {
            pokemonStats = it
        }
        assertNotNull(pokemonStats)
        assertEquals(pokemonStats[0].stat?.name, "hp")
        assertEquals(pokemonStats[0].base_stat, 45)
        assertEquals(pokemonStats[1].stat?.name, "attack")
        assertEquals(pokemonStats[1].base_stat, 49)
        assertEquals(pokemonStats[2].stat?.name, "defense")
        assertEquals(pokemonStats[2].base_stat, 49)
        assertEquals(pokemonStats[3].stat?.name, "special-attack")
        assertEquals(pokemonStats[3].base_stat, 65)
        assertEquals(pokemonStats[4].stat?.name, "special-defense")
        assertEquals(pokemonStats[4].base_stat, 65)
        assertEquals(pokemonStats[5].stat?.name, "speed")
        assertEquals(pokemonStats[5].base_stat, 45)
    }

    @Test
    fun getPokemonApi2() = runBlocking {
        var pokemonStats = arrayListOf<PokemonStat>()
        detailPokemonRepository.getPokemonApi("2").collect {
            pokemonStats = it
        }
        assertNotNull(pokemonStats)
        assertEquals(pokemonStats[0].stat?.name, "hp")
        assertEquals(pokemonStats[0].base_stat, 60)
        assertEquals(pokemonStats[1].stat?.name, "attack")
        assertEquals(pokemonStats[1].base_stat, 62)
        assertEquals(pokemonStats[2].stat?.name, "defense")
        assertEquals(pokemonStats[2].base_stat, 63)
        assertEquals(pokemonStats[3].stat?.name, "special-attack")
        assertEquals(pokemonStats[3].base_stat, 80)
        assertEquals(pokemonStats[4].stat?.name, "special-defense")
        assertEquals(pokemonStats[4].base_stat, 80)
        assertEquals(pokemonStats[5].stat?.name, "speed")
        assertEquals(pokemonStats[5].base_stat, 60)
    }

    @Test
    fun getPokemonApi3() = runBlocking {
        var pokemonStats = arrayListOf<PokemonStat>()
        detailPokemonRepository.getPokemonApi("3").collect {
            pokemonStats = it
        }
        assertNotNull(pokemonStats)
        assertEquals(pokemonStats[0].stat?.name, "hp")
        assertEquals(pokemonStats[0].base_stat, 80)
        assertEquals(pokemonStats[1].stat?.name, "attack")
        assertEquals(pokemonStats[1].base_stat, 82)
        assertEquals(pokemonStats[2].stat?.name, "defense")
        assertEquals(pokemonStats[2].base_stat, 83)
        assertEquals(pokemonStats[3].stat?.name, "special-attack")
        assertEquals(pokemonStats[3].base_stat, 100)
        assertEquals(pokemonStats[4].stat?.name, "special-defense")
        assertEquals(pokemonStats[4].base_stat, 100)
        assertEquals(pokemonStats[5].stat?.name, "speed")
        assertEquals(pokemonStats[5].base_stat, 80)
    }

    //Run after clicking item 1 in app
    @Test
    fun getPokemonLocal1() = runBlocking {
        var pokemonStats = arrayListOf<PokemonStat>()
        detailPokemonRepository.getLocalPokemonStat("1").collect { entity ->
            entity.stats?.let { pokemonStats = it }
        }
        assertNotNull(pokemonStats)
        assertEquals(pokemonStats[0].stat?.name, "hp")
        assertEquals(pokemonStats[0].base_stat, 45)
        assertEquals(pokemonStats[1].stat?.name, "attack")
        assertEquals(pokemonStats[1].base_stat, 49)
        assertEquals(pokemonStats[2].stat?.name, "defense")
        assertEquals(pokemonStats[2].base_stat, 49)
        assertEquals(pokemonStats[3].stat?.name, "special-attack")
        assertEquals(pokemonStats[3].base_stat, 65)
        assertEquals(pokemonStats[4].stat?.name, "special-defense")
        assertEquals(pokemonStats[4].base_stat, 65)
        assertEquals(pokemonStats[5].stat?.name, "speed")
        assertEquals(pokemonStats[5].base_stat, 45)
    }

    //Run after clicking item 2 in app
    @Test
    fun getPokemonLocal2() = runBlocking {
        var pokemonStats = arrayListOf<PokemonStat>()
        detailPokemonRepository.getLocalPokemonStat("2").collect { entity ->
            entity.stats?.let { pokemonStats = it }
        }
        assertNotNull(pokemonStats)
        assertEquals(pokemonStats[0].stat?.name, "hp")
        assertEquals(pokemonStats[0].base_stat, 60)
        assertEquals(pokemonStats[1].stat?.name, "attack")
        assertEquals(pokemonStats[1].base_stat, 62)
        assertEquals(pokemonStats[2].stat?.name, "defense")
        assertEquals(pokemonStats[2].base_stat, 63)
        assertEquals(pokemonStats[3].stat?.name, "special-attack")
        assertEquals(pokemonStats[3].base_stat, 80)
        assertEquals(pokemonStats[4].stat?.name, "special-defense")
        assertEquals(pokemonStats[4].base_stat, 80)
        assertEquals(pokemonStats[5].stat?.name, "speed")
        assertEquals(pokemonStats[5].base_stat, 60)
    }

    //Run after clicking item 3 in app
    @Test
    fun getPokemonLocal3() = runBlocking {
        var pokemonStats = arrayListOf<PokemonStat>()
        detailPokemonRepository.getLocalPokemonStat("3").collect { entity ->
            entity.stats?.let { pokemonStats = it }
        }
        assertNotNull(pokemonStats)
        assertEquals(pokemonStats[0].stat?.name, "hp")
        assertEquals(pokemonStats[0].base_stat, 80)
        assertEquals(pokemonStats[1].stat?.name, "attack")
        assertEquals(pokemonStats[1].base_stat, 82)
        assertEquals(pokemonStats[2].stat?.name, "defense")
        assertEquals(pokemonStats[2].base_stat, 83)
        assertEquals(pokemonStats[3].stat?.name, "special-attack")
        assertEquals(pokemonStats[3].base_stat, 100)
        assertEquals(pokemonStats[4].stat?.name, "special-defense")
        assertEquals(pokemonStats[4].base_stat, 100)
        assertEquals(pokemonStats[5].stat?.name, "speed")
        assertEquals(pokemonStats[5].base_stat, 80)
    }
}