package com.cortes.animal.midterm

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cortes.animal.midterm.databinding.ActivityAnimalDetailsBinding

class AnimalDetailsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAnimalDetailsBinding.inflate(layoutInflater) }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.detailAnimalTitle.text = intent.getStringExtra("NAME_PARAMS")
        binding.detailAnimalDesc.text = intent.getStringExtra("DESCRIPTION_PARAMS")

        sharedPreferences = getSharedPreferences("BlockedAnimals", MODE_PRIVATE)

        binding.detailBlockAnimal.setOnClickListener {
            intent.getStringExtra("NAME_PARAMS")?.let { animalName ->
                blockAnimal(animalName)
            }
            finish()
        }
    }

    private fun blockAnimal(animalName: String) {
        val blockedAnimals = getBlockedAnimals().toMutableSet().apply { add(animalName) }
        sharedPreferences.edit().putStringSet("blocked_animals", blockedAnimals).apply()
    }

    private fun getBlockedAnimals(): Set<String> {
        return sharedPreferences.getStringSet("blocked_animals", setOf()) ?: setOf()
    }
}