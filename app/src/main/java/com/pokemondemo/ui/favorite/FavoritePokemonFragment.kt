package com.pokemondemo.ui.favorite

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pokemondemo.R
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.base.ui.BaseFragment
import com.pokemondemo.databinding.FragmentFavoritePokemonBinding
import com.pokemondemo.ui.PokemonSelectListener
import com.pokemondemo.ui.detail.DetailPokemonFragment
import com.pokemondemo.ui.extention.onTextChanged
import com.pokemondemo.ui.favorite.mvi.FavoritePokemonIntent
import com.pokemondemo.ui.favorite.mvi.FavoritePokemonState
import com.pokemondemo.utils.view.RecyclerViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePokemonFragment : BaseFragment(), PokemonSelectListener {

    private var _binding: FragmentFavoritePokemonBinding? = null

    private lateinit var viewModel: FavoritePokemonViewModel

    private val binding get() = _binding!!

    private lateinit var favoritePokemonAdapter: FavoritePokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[FavoritePokemonViewModel::class.java]
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_pokemon, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.processIntent(FavoritePokemonIntent.FetchData(favoritePokemonAdapter.itemCount))
        lifecycleScope.launchWhenResumed {
            viewModel.getState().observe(viewLifecycleOwner, ::onCallBackState)
        }
    }

    private fun initViews() {
        binding.rvFavorite.layoutManager = GridLayoutManager(activity, 2)
        favoritePokemonAdapter = FavoritePokemonAdapter(this)
        binding.adapter = favoritePokemonAdapter
        binding.rvFavorite.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(view, dx, dy)
                if (RecyclerViewUtils.isLastItemVisible(view, favoritePokemonAdapter.itemCount)) {
                    onLastItemScrolled()
                }
            }
        })
        binding.etSearch.onTextChanged {
            viewModel.processIntent(FavoritePokemonIntent.SearchPokemon(it))
        }
        binding.swipeFavorite.setOnRefreshListener { loadData() }
    }

    private fun onLastItemScrolled() {
        if (TextUtils.isEmpty(
                binding.etSearch.text.trim().toString()
            ) && favoritePokemonAdapter.itemCount % 50 == 0
        ) {
            FavoritePokemonIntent.FetchData(favoritePokemonAdapter.itemCount)
        }
    }

    private fun loadData(offset: Int = 0) {
        if (!TextUtils.isEmpty(binding.etSearch.text.trim().toString())) {
            viewModel.processIntent(
                FavoritePokemonIntent.SearchPokemon(binding.etSearch.text.trim().toString())
            )
        } else {
            viewModel.processIntent(FavoritePokemonIntent.FetchData(offset))
        }
    }

    private fun onCallBackState(state: FavoritePokemonState) {
        when (state) {
            is FavoritePokemonState.OnLoading -> {
                showLoadingDialog()
            }
            is FavoritePokemonState.OnSuccess -> {
                hideLoadingDialog()
                binding.swipeFavorite.isRefreshing = false
                favoritePokemonAdapter.submitList(state.dataList.toMutableList())
            }
            is FavoritePokemonState.OnError -> {
                hideLoadingDialog()
                binding.swipeFavorite.isRefreshing = false
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        saveStateAction()
    }

    private fun saveStateAction() {
        favoritePokemonAdapter.let {
            if (it.itemCount > 0) {
                binding.rvFavorite.scrollToPosition(it.itemCount - 1)
                viewModel.processIntent(FavoritePokemonIntent.SaveSuccessState)
            }
        }
    }

    override fun onPokemonSelected(pokemon: Pokemon) =
        DetailPokemonFragment.newInstance(pokemon)
            .show(childFragmentManager, DetailPokemonFragment.TAG)
}