package com.pokemondemo.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.api.model.PokemonStat
import com.pokemondemo.base.CoroutinesDispatcherProvider
import com.pokemondemo.base.mvi.BaseViewModel
import com.pokemondemo.repository.DetailPokemonRepository
import com.pokemondemo.roomdatabase.entity.PokemonStatEntity
import com.pokemondemo.ui.detail.mvi.DetailPokemonIntent
import com.pokemondemo.ui.detail.mvi.DetailPokemonState
import com.pokemondemo.utils.PokemonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val detailPokemonRepository: DetailPokemonRepository,
    private val dispatcher: CoroutinesDispatcherProvider
) : BaseViewModel<DetailPokemonIntent, DetailPokemonState>() {

    override val state: MutableLiveData<DetailPokemonState> = MutableLiveData()
    private var statList = ArrayList<PokemonStat>()

    override fun processIntent(intent: DetailPokemonIntent) {
        state.postValue(DetailPokemonState.OnLoading)
        if (intent is DetailPokemonIntent.FetchData) {
            viewModelScope.launch(dispatcher.Main) {
                loadLocalStats(intent)
            }
        }
    }

    private suspend fun loadLocalStats(intent: DetailPokemonIntent.FetchData) {
        try {
            detailPokemonRepository.getLocalPokemonStat(intent.pokemon.id).collect {
                if (it != null && !it.stats.isNullOrEmpty()) {
                    updateStateSuccess(intent.pokemon, it.stats)
                } else {
                    loadRemoteStats(intent)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onErrorMessage(e.message.toString())
        }
    }

    private fun updateStateSuccess(pokemon: Pokemon, it: ArrayList<PokemonStat>?) {
        state.postValue(
            DetailPokemonState.OnSuccess(PokemonUtils.updatePokemon(pokemon, it))
        )
    }

    private suspend fun loadRemoteStats(intent: DetailPokemonIntent.FetchData) {
        detailPokemonRepository.getPokemonApi(intent.pokemon.id)
            .collect { newList ->
                statList = newList
                detailPokemonRepository.insertLocalPokemonStat(
                    PokemonStatEntity(intent.pokemon.id?.toLong(), newList)
                )
            }
        if (!statList.isNullOrEmpty()) {
            updateStateSuccess(intent.pokemon, statList)
        } else {
            onErrorMessage("No data!")
        }
    }

    private fun onErrorMessage(message: String) {
        state.postValue(DetailPokemonState.OnError(message))
    }
}