package com.pokemondemo.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pokemondemo.R
import com.pokemondemo.api.model.Pokemon
import com.pokemondemo.databinding.LayoutFavoritePokemonItemsBinding
import com.pokemondemo.ui.PokemonSelectListener

class FavoritePokemonAdapter(private val pokemonSelectListener: PokemonSelectListener) :
    ListAdapter<Pokemon, FavoritePokemonAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutFavoritePokemonItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_favorite_pokemon_items,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), pokemonSelectListener)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class ViewHolder(private val binding: LayoutFavoritePokemonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon, selectListener: PokemonSelectListener) {
            binding.pokemon = pokemon
            binding.listener = selectListener
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

}