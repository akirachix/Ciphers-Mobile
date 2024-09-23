package com.akirachix.totosteps

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.totosteps.activity.TeaserOne
import com.akirachix.totosteps.databinding.ActivityAssessmentResourcesAllBinding

class AssessmentResourcesAllActivity : AppCompatActivity() {
    lateinit var binding: ActivityAssessmentResourcesAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessmentResourcesAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, Assessement_child_resource::class.java)
            startActivity(intent)

            finish()
        }, 2000)



        binding.rvChildren.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        displayResources()



    }

    fun displayResources(){
        val resource1 = resources("","Teach your child how to crawl and walk")
        val resource2 = resources("","Teach your child how to crawl and walk")
        val resource3 = resources("","Teach your child how to crawl and walk")
        val resource4 = resources("","Teach your child how to crawl and walk")
        val resource5 = resources("","Teach your child how to crawl and walk")
        val resource6 = resources("","Teach your child how to crawl and walk")
        val resource7 = resources("","Teach your child how to crawl and walk")

        var resources = listOf(resource1,resource2,resource3,resource4,resource5,resource6,resource7)

        var resourcesAdapter = ResourceAdapter(resources)
        binding.rvChildren.adapter = resourcesAdapter
    }
}

