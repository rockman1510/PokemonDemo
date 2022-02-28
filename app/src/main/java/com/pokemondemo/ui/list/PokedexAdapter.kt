package com.pokemondemo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pokemondemo.R
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.databinding.LayoutPokedexItemsBinding
import com.pokemondemo.ui.PokemonSelectListener

class PokedexAdapter(
    private val pokemonSelectListener: PokemonSelectListener,
    private val pokedexCheckedListener: PokedexCheckedListener
) : ListAdapter<Pokemon, PokedexAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutPokedexItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_pokedex_items, parent, false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), pokemonSelectListener, pokedexCheckedListener)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class ViewHolder(val binding: LayoutPokedexItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            pokemon: Pokemon,
            selectListener: PokemonSelectListener,
            checkListener: PokedexCheckedListener
        ) {
            binding.pokemon = pokemon
            binding.selectListener = selectListener
            binding.checkListener = checkListener
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }

    }

    interface PokedexCheckedListener {
        fun onCheckChanged(pokemon: Pokemon, isCheck: Boolean)
    }
}
