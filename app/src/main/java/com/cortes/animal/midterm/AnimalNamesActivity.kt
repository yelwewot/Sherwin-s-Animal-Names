package com.cortes.animal.midterm

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cortes.animal.midterm.databinding.ActivityAnimalNamesBinding

class AnimalNamesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimalNamesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: ListAnimals
    private val animalList = mutableListOf<AnimalAdapter.AnimalDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalNamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVariables()
        setupRecyclerView()
        setManageBlockAnimalsButtonListener()
    }

    private fun initVariables() {
        sharedPreferences = getSharedPreferences("BlockedAnimals", MODE_PRIVATE)
        viewModel = ViewModelProvider(this)[ListAnimals::class.java]
        animalList.addAll(viewModel.getAnimalList())
    }

    private fun setupRecyclerView() {
        val adapter = AnimalAdapter(animalList, ::onItemClick)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@AnimalNamesActivity)
            addItemDecoration(DividerItemDecoration(this@AnimalNamesActivity, LinearLayoutManager.HORIZONTAL))
            this.adapter = adapter
        }
    }

    private fun setManageBlockAnimalsButtonListener() {
        binding.nameBlockAnimal.setOnClickListener {
            startActivity(Intent(this, ManageBlockActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refreshAnimalList()
    }

    private fun onItemClick(item: AnimalAdapter.AnimalDataModel) {
        startAnimalDetailsActivity(item.name, item.description)
    }

    private fun startAnimalDetailsActivity(name: String, description: String) {
        Intent(this, AnimalDetailsActivity::class.java).apply {
            putExtra("NAME_PARAMS", name)
            putExtra("DESCRIPTION_PARAMS", description)
        }.also { intent ->
            startActivity(intent)
        }
    }

    private fun refreshAnimalList() {
        val filteredAnimalList = getFilteredAnimalList()
        binding.recyclerView.adapter = AnimalAdapter(filteredAnimalList, ::onItemClick)
    }

    private fun getFilteredAnimalList(): List<AnimalAdapter.AnimalDataModel> {
        val blockedAnimals = getBlockedAnimals()
        return animalList.filter { animal -> animal.name !in blockedAnimals }
    }

    private fun getBlockedAnimals(): Set<String> {
        return sharedPreferences.getStringSet("blocked_animals", setOf()) ?: setOf()
    }
}