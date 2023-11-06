package com.cortes.animal.midterm

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cortes.animal.midterm.databinding.ActivityManageBlockBinding

class ManageBlockActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageBlockBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageBlockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeSharedPreferences()
        setupBlockedAnimalsRecyclerView()
    }

    private fun initializeSharedPreferences() {
        sharedPreferences = getSharedPreferences("BlockedAnimals", MODE_PRIVATE)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupBlockedAnimalsRecyclerView() {
        val blockedAnimals = getBlockedAnimals().toList()
        val adapter = BlockedAnimalsAdapter(blockedAnimals)

        binding.blockedAnimalsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ManageBlockActivity)
            this.adapter = adapter
        }

        adapter.setOnUnblockClickListener { animalName ->
            unblockAnimal(animalName)
            adapter.notifyDataSetChanged()
        }
    }

    private fun getBlockedAnimals(): Set<String> {
        return sharedPreferences.getStringSet("blocked_animals", setOf()) ?: setOf()
    }

    private fun unblockAnimal(animalName: String) {
        val blockedAnimals = getBlockedAnimals().toMutableSet()
        blockedAnimals.remove(animalName)
        updateBlockedAnimalsInSharedPreferences(blockedAnimals)
    }

    private fun updateBlockedAnimalsInSharedPreferences(blockedAnimals: MutableSet<String>) {
        sharedPreferences.edit().putStringSet("blocked_animals", blockedAnimals).apply()
    }
}