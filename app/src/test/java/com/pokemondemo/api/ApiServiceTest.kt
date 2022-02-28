package com.pokemondemo.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pokemondemo.BuildConfig
import com.pokemondemo.api.request.ListPokemonQueryBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get 20 Pokedex`() = runBlocking {
        val queryBuilder =
            ListPokemonQueryBuilder().setOffset(0).setLimit(20)
        val listPokedex = service.getPokemonListApi(queryBuilder.build())
        Assert.assertEquals(listPokedex.body()?.results?.size, 20)
    }

    @Test
    fun `get 50 Pokedex`() = runBlocking {
        val queryBuilder =
            ListPokemonQueryBuilder().setOffset(0).setLimit(50)
        val listPokedex = service.getPokemonListApi(queryBuilder.build())
        Assert.assertEquals(listPokedex.body()?.results?.size, 50)
    }

    @Test
    fun `get Pokemon 1 - bulbasaur`() = runBlocking {
        val pokemonResponse = service.getPokemonApi("1")
        Assert.assertEquals(pokemonResponse.body()?.name, "bulbasaur")
        Assert.assertNotNull(pokemonResponse.body()?.stats)
    }

    @Test
    fun `get Pokemon 2 - ivysaur`() = runBlocking {
        val pokemonResponse = service.getPokemonApi("2")
        Assert.assertEquals(pokemonResponse.body()?.name, "ivysaur")
        Assert.assertNotNull(pokemonResponse.body()?.stats)
    }

    @Test
    fun `get Pokemon 3 - venusaur`() = runBlocking {
        val pokemonResponse = service.getPokemonApi("3")
        Assert.assertEquals(pokemonResponse.body()?.name, "venusaur")
        Assert.assertNotNull(pokemonResponse.body()?.stats)
    }
}