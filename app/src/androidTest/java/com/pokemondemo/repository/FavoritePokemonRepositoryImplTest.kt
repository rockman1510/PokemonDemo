package com.pokemondemo.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.utils.PokemonUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FavoritePokemonRepositoryImplTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var favoriteRepository: FavoritePokemonRepository

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    // Make sure the app loaded data successfully and remove all favorite pokemon
    @Test
    fun getNoneLocalFavoritePokemon() = runBlocking {
        var dataList = arrayListOf<Pokemon>()
        favoriteRepository.getLocalFavoritePokemonList(0, 50).collect {
            dataList = PokemonUtils.convertToPokemonModels(ArrayList(it))
        }
        assertEquals(dataList.size, 0)
    }

    // Make sure the app loaded data successfully and add at least one favorite pokemon
    @Test
    fun getLocalFavoritePokemon() = runBlocking {
        var dataList = arrayListOf<Pokemon>()
        favoriteRepository.getLocalFavoritePokemonList(0, 50).collect {
            dataList = PokemonUtils.convertToPokemonModels(ArrayList(it))
        }
        assert(dataList.size > 0)
    }

    // Make sure the app loaded data successfully and pokemon has character "sa" from name into favorite
    @Test
    fun searchFavoritePokemon() = runBlocking {
        var dataList = arrayListOf<Pokemon>()
        favoriteRepository.searchFavoritePokemon("sa").collect {
            dataList = PokemonUtils.convertToPokemonModels(ArrayList(it))
        }
        assert(dataList.size > 0)
    }

    // Make sure the app loaded data successfully and has no pokemon containing character "az" from name into favorite
    @Test
    fun searchNoneFavoritePokemon() = runBlocking {
        var dataList = arrayListOf<Pokemon>()
        favoriteRepository.searchFavoritePokemon("az").collect {
            dataList = PokemonUtils.convertToPokemonModels(ArrayList(it))
        }
        assertEquals(dataList.size, 0)
    }

}