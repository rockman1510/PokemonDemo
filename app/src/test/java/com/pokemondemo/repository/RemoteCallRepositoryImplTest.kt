package com.pokemondemo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pokemondemo.BuildConfig
import com.pokemondemo.MainCoroutineRule
import com.pokemondemo.api.ApiService
import com.pokemondemo.api.model.Pokedex
import com.pokemondemo.api.model.PokemonStat
import com.pokemondemo.api.request.ListPokemonQueryBuilder
import com.pokemondemo.base.CoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RemoteCallRepositoryImplTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var fakeRepositoryForRemoteImpl: FakeRepositoryForRemoteImpl

    @Before
    fun setUp() {
        val dispatcherProvider = CoroutinesDispatcherProvider(
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher
        )
        mockWebServer = MockWebServer()
        val service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
        fakeRepositoryForRemoteImpl = FakeRepositoryForRemoteImpl(service, dispatcherProvider)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getListPokemonApi - 20`() = runBlocking {
        var dataList = arrayListOf<Pokedex>()
        val queryBuilder =
            ListPokemonQueryBuilder().setOffset(100).setLimit(20)
        fakeRepositoryForRemoteImpl.getListPokemonApi(queryBuilder).collect {
            dataList = it
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList[0].url, "https://pokeapi.co/api/v2/pokemon/101/")
        Assert.assertEquals(
            dataList[dataList.size - 1].url,
            "https://pokeapi.co/api/v2/pokemon/120/"
        )
        Assert.assertEquals(dataList.size, 20)
    }

    @Test
    fun `getListPokemonApi - 50`() = runBlocking {
        var dataList = arrayListOf<Pokedex>()
        val queryBuilder =
            ListPokemonQueryBuilder().setOffset(0).setLimit(50)
        fakeRepositoryForRemoteImpl.getListPokemonApi(queryBuilder).collect {
            dataList = it
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList[0].url, "https://pokeapi.co/api/v2/pokemon/1/")
        Assert.assertEquals(
            dataList[dataList.size - 1].url,
            "https://pokeapi.co/api/v2/pokemon/50/"
        )
        Assert.assertEquals(dataList.size, 50)
    }

    @Test
    fun `getPokemonApi 1 - bulbasaur`() = runBlocking {
        var pokemonStats = arrayListOf<PokemonStat>()
        fakeRepositoryForRemoteImpl.getPokemonApi("1").collect {
            pokemonStats = it
        }
        Assert.assertNotNull(pokemonStats)
        Assert.assertEquals(pokemonStats[0].stat?.name, "hp")
        Assert.assertEquals(pokemonStats[0].base_stat, 45)
        Assert.assertEquals(pokemonStats[1].stat?.name, "attack")
        Assert.assertEquals(pokemonStats[1].base_stat, 49)
        Assert.assertEquals(pokemonStats[2].stat?.name, "defense")
        Assert.assertEquals(pokemonStats[2].base_stat, 49)
        Assert.assertEquals(pokemonStats[3].stat?.name, "special-attack")
        Assert.assertEquals(pokemonStats[3].base_stat, 65)
        Assert.assertEquals(pokemonStats[4].stat?.name, "special-defense")
        Assert.assertEquals(pokemonStats[4].base_stat, 65)
        Assert.assertEquals(pokemonStats[5].stat?.name, "speed")
        Assert.assertEquals(pokemonStats[5].base_stat, 45)
    }

    @Test
    fun `get Pokemon 2 - ivysaur`() = runBlocking {
        var pokemonStats = arrayListOf<PokemonStat>()
        fakeRepositoryForRemoteImpl.getPokemonApi("2").collect {
            pokemonStats = it
        }
        Assert.assertNotNull(pokemonStats)
        Assert.assertEquals(pokemonStats[0].stat?.name, "hp")
        Assert.assertEquals(pokemonStats[0].base_stat, 60)
        Assert.assertEquals(pokemonStats[1].stat?.name, "attack")
        Assert.assertEquals(pokemonStats[1].base_stat, 62)
        Assert.assertEquals(pokemonStats[2].stat?.name, "defense")
        Assert.assertEquals(pokemonStats[2].base_stat, 63)
        Assert.assertEquals(pokemonStats[3].stat?.name, "special-attack")
        Assert.assertEquals(pokemonStats[3].base_stat, 80)
        Assert.assertEquals(pokemonStats[4].stat?.name, "special-defense")
        Assert.assertEquals(pokemonStats[4].base_stat, 80)
        Assert.assertEquals(pokemonStats[5].stat?.name, "speed")
        Assert.assertEquals(pokemonStats[5].base_stat, 60)
    }

    @Test
    fun `get Pokemon 3 - venusaur`() = runBlocking {
        var pokemonStats = arrayListOf<PokemonStat>()
        fakeRepositoryForRemoteImpl.getPokemonApi("3").collect {
            pokemonStats = it
        }
        Assert.assertNotNull(pokemonStats)
        Assert.assertEquals(pokemonStats[0].stat?.name, "hp")
        Assert.assertEquals(pokemonStats[0].base_stat, 80)
        Assert.assertEquals(pokemonStats[1].stat?.name, "attack")
        Assert.assertEquals(pokemonStats[1].base_stat, 82)
        Assert.assertEquals(pokemonStats[2].stat?.name, "defense")
        Assert.assertEquals(pokemonStats[2].base_stat, 83)
        Assert.assertEquals(pokemonStats[3].stat?.name, "special-attack")
        Assert.assertEquals(pokemonStats[3].base_stat, 100)
        Assert.assertEquals(pokemonStats[4].stat?.name, "special-defense")
        Assert.assertEquals(pokemonStats[4].base_stat, 100)
        Assert.assertEquals(pokemonStats[5].stat?.name, "speed")
        Assert.assertEquals(pokemonStats[5].base_stat, 80)
    }
}