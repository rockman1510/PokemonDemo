package com.pokemondemo.ui.list

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pokemondemo.R
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.base.ui.BaseFragment
import com.pokemondemo.databinding.FragmentListPokedexBinding
import com.pokemondemo.ui.PokemonSelectListener
import com.pokemondemo.ui.detail.DetailPokemonFragment
import com.pokemondemo.ui.extention.onTextChanged
import com.pokemondemo.ui.list.mvi.ListPokedexIntent
import com.pokemondemo.ui.list.mvi.ListPokemonState
import com.pokemondemo.utils.view.RecyclerViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPokedexFragment : BaseFragment(), PokemonSelectListener,
    PokedexAdapter.PokedexCheckedListener {

    private lateinit var viewModel: ListPokedexViewModel

    private var _binding: FragmentListPokedexBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokedexAdapter: PokedexAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_pokedex, container, false)
        viewModel = ViewModelProvider(this)[ListPokedexViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getState().observe(viewLifecycleOwner, ::onCallBackState)
    }

    override fun onStop() {
        super.onStop()
        saveStateAction()
    }

    private fun saveStateAction() {
        pokedexAdapter.let {
            if (it.itemCount > 0 && TextUtils.isEmpty(binding.etSearch.text.trim().toString())) {
                binding.rvPokemon.scrollToPosition(it.itemCount - 5)
                viewModel.processIntent(ListPokedexIntent.SaveSuccessState)
            }
        }
    }

    private fun initViews() {
        binding.rvPokemon.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        pokedexAdapter = PokedexAdapter(this, this)
        binding.adapter = pokedexAdapter
        binding.rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(view, dx, dy)
                if (RecyclerViewUtils.isLastItemVisible(view, pokedexAdapter.itemCount)) {
                    onLastItemScrolled()
                }
            }
        })

        binding.swipeLayout.setOnRefreshListener { loadData() }
        binding.etSearch.onTextChanged {
            viewModel.processIntent(ListPokedexIntent.SearchPokedex(it))
        }
    }

    private fun onLastItemScrolled() {
        if (TextUtils.isEmpty(binding.etSearch.text.trim().toString())) {
            viewModel.processIntent(ListPokedexIntent.FetchData(pokedexAdapter.itemCount))
        }
    }

    private fun loadData(offset: Int = 0) {
        if (!TextUtils.isEmpty(binding.etSearch.text.trim().toString())) {
            viewModel.processIntent(
                ListPokedexIntent.SearchPokedex(binding.etSearch.text.trim().toString())
            )
        } else {
            viewModel.processIntent(ListPokedexIntent.FetchData(offset))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCallBackState(state: ListPokemonState) {
        when (state) {
            is ListPokemonState.OnLoading -> {
                showLoadingDialog()
            }
            is ListPokemonState.OnSuccess -> {
                hideLoadingDialog()
                binding.swipeLayout.isRefreshing = false
                pokedexAdapter.submitList(state.dataList.toMutableList())
            }
            is ListPokemonState.OnError -> {
                hideLoadingDialog()
                binding.swipeLayout.isRefreshing = false
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPokemonSelected(pokemon: Pokemon) =
        DetailPokemonFragment.newInstance(pokemon)
            .show(childFragmentManager, DetailPokemonFragment.TAG)

    override fun onCheckChanged(pokemon: Pokemon, isCheck: Boolean) {
        viewModel.processIntent(ListPokedexIntent.UpdatePokedex(pokemon, isCheck))
    }
}