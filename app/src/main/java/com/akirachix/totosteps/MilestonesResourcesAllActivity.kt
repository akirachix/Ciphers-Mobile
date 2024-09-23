package com.akirachix.totosteps

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.totosteps.activity.TeaserOne
import com.akirachix.totosteps.databinding.ActivityAllResourcesBinding

class MilestonesResourcesAllActivity : AppCompatActivity() {

    lateinit var binding: ActivityAllResourcesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllResourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvChildren.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        displayResources()

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MilestoneResources::class.java)
            startActivity(intent)

            finish()
        }, 2000)



    }

    fun displayResources(){
        val resource1 = resources("","Baby at  2 Months")
        val resource2 = resources("","Baby at  4 Months")
        val resource3 = resources("","Baby at  6 Months")
        val resource4 = resources("","Baby at  8 Months")
        val resource5 = resources("","Baby at  10 Months")
        val resource6 = resources("","Baby at  12 Months")
        val resource7 = resources("","Baby at  14 Months")

        var resources = listOf(resource1,resource2,resource3,resource4,resource5,resource6,resource7)

        var resourcesAdapter = ResourceAdapter(resources)
        binding.rvChildren.adapter = resourcesAdapter
    }
}