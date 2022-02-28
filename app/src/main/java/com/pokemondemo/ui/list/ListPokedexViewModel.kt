package com.pokemondemo.ui.list

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.api.request.ListPokemonQueryBuilder
import com.pokemondemo.base.CoroutinesDispatcherProvider
import com.pokemondemo.base.mvi.BaseViewModel
import com.pokemondemo.repository.ListPokemonRepository
import com.pokemondemo.ui.list.mvi.ListPokedexIntent
import com.pokemondemo.ui.list.mvi.ListPokemonState
import com.pokemondemo.utils.PokemonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPokedexViewModel @Inject constructor(
    private val listPokemonRepository: ListPokemonRepository,
    private val dispatcher: CoroutinesDispatcherProvider,
) : BaseViewModel<ListPokedexIntent, ListPokemonState>() {

    override val state: MutableLiveData<ListPokemonState> = MutableLiveData()
    private val pokemonList = arrayListOf<Pokemon>()
    private var pokemonSearchList = arrayListOf<Pokemon>()

    init {
        processIntent(ListPokedexIntent.FetchData(0))
    }

    override fun processIntent(intent: ListPokedexIntent) {
        viewModelScope.launch(dispatcher.Main) {
            when (intent) {
                is ListPokedexIntent.FetchData -> {
                    loadLocalData(intent)
                }
                is ListPokedexIntent.SearchPokedex -> {
                    searchLocalData(intent)
                }
                is ListPokedexIntent.UpdatePokedex -> {
                    updatePokemon(intent)
                }
                is ListPokedexIntent.SaveSuccessState -> {
                    state.postValue(ListPokemonState.OnSuccess(pokemonList))
                }
            }
        }
    }

    private suspend fun loadLocalData(intent: ListPokedexIntent.FetchData) {
        state.postValue(ListPokemonState.OnLoading)
        if (intent.offset == 0)
            pokemonList.clear()
        listPokemonRepository.getLocalListPokemon(intent.offset, intent.limit)
            .collect {
                if (!it.isNullOrEmpty()) {
                    val newList = PokemonUtils.convertToPokemonModels(ArrayList(it))
                    if (!pokemonList.containsAll(newList))
                        pokemonList.addAll(newList)
                    state.postValue(ListPokemonState.OnSuccess(pokemonList))
                } else {
                    loadRemoteData(intent)
                }
            }
    }

    private suspend fun loadRemoteData(intent: ListPokedexIntent.FetchData) {
        try {
            val queryBuilder =
                ListPokemonQueryBuilder().setLimit(intent.limit)
                    .setOffset(intent.offset)
            var newData = ArrayList<Pokemon>()
            listPokemonRepository.getListPokemonApi(queryBuilder).collect {
                newData = PokemonUtils.fromResponseToPokemonModel(it)
                if (!newData.isNullOrEmpty()) {
                    pokemonList.addAll(newData)
                    listPokemonRepository.insertLocalListPokemon(
                        PokemonUtils.convertToPokemonEntities(newData)
                    )
                }
            }
            if (!newData.isNullOrEmpty()) {
                state.postValue(ListPokemonState.OnSuccess(pokemonList))
            } else {
                onError("No data!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message.toString())
        }
    }

    private suspend fun searchLocalData(intent: ListPokedexIntent.SearchPokedex) {
        if (!TextUtils.isEmpty(intent.name)) {
            listPokemonRepository.searchPokemon(intent.name).collect {
                pokemonSearchList = PokemonUtils.convertToPokemonModels(ArrayList(it))
                state.postValue(ListPokemonState.OnSuccess(pokemonSearchList))
            }
        } else if (!pokemonList.isNullOrEmpty()) {
            state.postValue(ListPokemonState.OnSuccess(pokemonList))
        }
    }

    private suspend fun updatePokemon(intent: ListPokedexIntent.UpdatePokedex) {
        val newPokemon = PokemonUtils.updatePokemon(intent.pokemon, intent.isFavorite)
        listPokemonRepository.updateLocalPokemon(
            PokemonUtils.fromModelToEntity(newPokemon)
        )
        val pokemon = pokemonList.find { (it.id == intent.pokemon.id) }
        val pokemonSearched = pokemonSearchList.find { (it.id == intent.pokemon.id) }
        pokemon?.let { pokemonList[pokemonList.indexOf(it)] = newPokemon }
        pokemonSearched?.let { pokemonSearchList[pokemonSearchList.indexOf(it)] = newPokemon }
    }

    private fun onError(errorMessage: String) {
        state.postValue(ListPokemonState.OnError(errorMessage))
    }
}