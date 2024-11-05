package com.akirachix.totosteps.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityResourceDetailsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ResourceDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityResourceDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResourceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val resourceTitle = intent.getStringExtra("RESOURCE_TITLE") ?: "No Title"
        val resourceTipsJson = intent.getStringExtra("RESOURCE_TIPS") ?: "[]"
        val resourceActivitiesJson = intent.getStringExtra("RESOURCE_ACTIVITIES") ?: "[]"

        val gson = Gson()
        val listType = object : TypeToken<List<String>>() {}.type


        val resourceTips: List<String> = gson.fromJson(resourceTipsJson, listType)
        val resourcesActivities: List<String> = gson.fromJson(resourceActivitiesJson, listType)


        binding.tvResourceTitle.text = resourceTitle


        val tipsText = resourceTips.joinToString("\n") { it }
        val activitiesText = resourcesActivities.joinToString("\n") { it }


        binding.tvTips.text = tipsText.trim()
        binding.tvActivities.text = activitiesText.trim()


        Log.d("ResourceDetails", "Tips: $tipsText")
        Log.d("ResourceDetails", "Activities: $activitiesText")
    }
}
