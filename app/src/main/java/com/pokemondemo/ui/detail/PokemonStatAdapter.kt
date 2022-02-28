package com.pokemondemo.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pokemondemo.R
import com.pokemondemo.api.model.PokemonStat
import com.pokemondemo.databinding.LayoutStatItemsBinding

class PokemonStatAdapter : RecyclerView.Adapter<PokemonStatAdapter.ViewHolder>() {

    private var dataList: ArrayList<PokemonStat> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutStatItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_stat_items, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newDataList: ArrayList<PokemonStat>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


    class ViewHolder(private val binding: LayoutStatItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonStat: PokemonStat) {
            binding.pokemonStat = pokemonStat
        }
    }
}