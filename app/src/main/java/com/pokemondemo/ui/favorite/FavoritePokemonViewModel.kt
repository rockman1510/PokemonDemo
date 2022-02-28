package com.pokemondemo.ui.favorite

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.base.CoroutinesDispatcherProvider
import com.pokemondemo.base.mvi.BaseViewModel
import com.pokemondemo.repository.FavoritePokemonRepository
import com.pokemondemo.roomdatabase.entity.PokemonEntity
import com.pokemondemo.ui.favorite.mvi.FavoritePokemonIntent
import com.pokemondemo.ui.favorite.mvi.FavoritePokemonState
import com.pokemondemo.utils.PokemonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePokemonViewModel @Inject constructor(
    private val favoritePokemonRepository: FavoritePokemonRepository,
    private val dispatcher: CoroutinesDispatcherProvider
) : BaseViewModel<FavoritePokemonIntent, FavoritePokemonState>() {

    override val state: MutableLiveData<FavoritePokemonState> = MutableLiveData()

    private var pokemonList = arrayListOf<Pokemon>()
    private var pokemonSearchList = arrayListOf<Pokemon>()

    override fun processIntent(intent: FavoritePokemonIntent) {
        viewModelScope.launch(dispatcher.Main) {
            when (intent) {
                is FavoritePokemonIntent.FetchData -> {
                    loadData(intent)
                }
                is FavoritePokemonIntent.SearchPokemon -> {
                    searchLocalData(intent)
                }
                is FavoritePokemonIntent.SaveSuccessState -> {
                    state.postValue(FavoritePokemonState.OnSuccess(pokemonList))
                }
            }
        }
    }

    private suspend fun loadData(intent: FavoritePokemonIntent.FetchData) {
        state.postValue(FavoritePokemonState.OnLoading)
        try {
            favoritePokemonRepository.getLocalFavoritePokemonList(
                intent.offset, intent.limit
            ).collect {
                processState(it, intent.offset)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message.toString())
        }
    }

    private fun processState(pokemonEntity: List<PokemonEntity>, offset: Int) {
        val newList = PokemonUtils.convertToPokemonModels(ArrayList(pokemonEntity))
        if (offset == 0) {
            pokemonList.clear()
            pokemonList = newList
        } else {
            pokemonList.addAll(newList)
        }
        if (!pokemonEntity.isNullOrEmpty()) {
            state.postValue(FavoritePokemonState.OnSuccess(pokemonList))
        } else {
            onError("No Data!")
        }
    }

    private suspend fun searchLocalData(intent: FavoritePokemonIntent.SearchPokemon) {
        if (!TextUtils.isEmpty(intent.name)) {
            favoritePokemonRepository.searchFavoritePokemon(intent.name).collect {
                pokemonSearchList = PokemonUtils.convertToPokemonModels(ArrayList(it))
                state.postValue(FavoritePokemonState.OnSuccess(pokemonSearchList))
            }
        } else if (!pokemonList.isNullOrEmpty()) {
            state.postValue(FavoritePokemonState.OnSuccess(pokemonList))
        }
    }

    private fun onError(message: String) {
        state.postValue(FavoritePokemonState.OnError(message))
    }
}