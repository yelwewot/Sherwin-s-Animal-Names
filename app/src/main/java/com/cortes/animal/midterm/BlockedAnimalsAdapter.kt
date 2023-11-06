package com.cortes.animal.midterm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cortes.animal.midterm.databinding.BlockedAnimalsBinding

class BlockedAnimalsAdapter(private val blockedAnimals: List<String>) :
    RecyclerView.Adapter<BlockedAnimalsAdapter.BlockedAnimalViewHolder>() {

    inner class BlockedAnimalViewHolder(private val binding: BlockedAnimalsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.unblockB.setOnClickListener {
                onUnblockClick(blockedAnimals[adapterPosition])
            }
        }

        fun bind(animalName: String) {
            binding.animalNameTV.text = animalName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockedAnimalViewHolder {
        val binding = BlockedAnimalsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BlockedAnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockedAnimalViewHolder, position: Int) {
        holder.bind(blockedAnimals[position])
    }

    override fun getItemCount(): Int {
        return blockedAnimals.size
    }

    private var onUnblockClick: (String) -> Unit = {}

    fun setOnUnblockClickListener(listener: (String) -> Unit) {
        onUnblockClick = listener
    }
}