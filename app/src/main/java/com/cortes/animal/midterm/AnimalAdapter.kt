package com.cortes.animal.midterm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cortes.animal.midterm.databinding.AnimalDetailsBinding

class AnimalAdapter(
    private val itemList: List<AnimalDataModel>,
    private val onItemClick: (AnimalDataModel) -> Unit
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    data class AnimalDataModel(val name: String, val description: String)

    inner class AnimalViewHolder(private val binding: AnimalDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(itemList[position])
                }
            }
        }

        fun bind(item: AnimalDataModel) {
            binding.animalName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = AnimalDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}