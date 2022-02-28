package com.pokemondemo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pokemondemo.R
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.databinding.FragmentDetailPokemonBinding
import com.pokemondemo.ui.detail.mvi.DetailPokemonIntent
import com.pokemondemo.ui.detail.mvi.DetailPokemonState
import com.pokemondemo.ui.extention.build
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPokemonFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    private lateinit var binding: FragmentDetailPokemonBinding

    private lateinit var detailPokemonViewModel: DetailPokemonViewModel

    private lateinit var pokemonStatAdapter: PokemonStatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_pokemon, container, false)
        detailPokemonViewModel = ViewModelProvider(this)[DetailPokemonViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        detailPokemonViewModel.getState().observe(viewLifecycleOwner, ::onCallBackState)
    }

    private fun initViews() {
        arguments?.getParcelable<Pokemon>(KEY_POKEMON)?.let {
            detailPokemonViewModel.processIntent(DetailPokemonIntent.FetchData(it))
            binding.pokemon = it
        }
        binding.rvStat.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        pokemonStatAdapter = PokemonStatAdapter()
        binding.adapter = pokemonStatAdapter
    }

    private fun onCallBackState(state: DetailPokemonState?) {
        when (state) {
            is DetailPokemonState.OnLoading -> {
                binding.pbLoading.visibility = View.VISIBLE
            }
            is DetailPokemonState.OnSuccess -> {
                binding.pbLoading.visibility = View.GONE
                binding.rvStat.visibility = View.VISIBLE
                state.pokemon.stats?.let { pokemonStatAdapter.updateData(it) }
            }
            is DetailPokemonState.OnError -> {
                binding.pbLoading.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        val TAG: String = DetailPokemonFragment::class.java.simpleName
        private const val KEY_POKEMON = "KEY_POKEMON"

        fun newInstance(
            pokemon: Pokemon
        ): DetailPokemonFragment = DetailPokemonFragment().build {
            putParcelable(KEY_POKEMON, pokemon)
        }
    }

}
