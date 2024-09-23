package com.akirachix.totosteps.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.akirachix.totosteps.AssessFragment
import com.akirachix.totosteps.ChildrenFragment
import com.akirachix.totosteps.HomeFragement
import com.akirachix.totosteps.MilestoneFragment
import com.akirachix.totosteps.R
import com.akirachix.totosteps.ResourcesFragment
import com.akirachix.totosteps.databinding.ActivityHomeScreenBinding

class HomeScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragement())
                    true
                }
                R.id.nav_assess -> {
                    loadFragment(AssessFragment())
                    true
                }
                R.id.nav_children -> {
                    loadFragment(ChildrenFragment())
                    true
                }
                R.id.nav_milestone ->{
                    loadFragment(MilestoneFragment())
                    true
                }
                R.id.nav_resources ->{
                    loadFragment(ResourcesFragment())
                    true
                }

                else -> true

            }

        }

        if(savedInstanceState == null){
            loadFragment(HomeFragement())
            binding.bottomNavigation.selectedItemId = R.id.nav_home
        }
        fun loadFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction().replace(R.id.fcvhome, fragment).commit()
        }
    }

    private  fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fcvhome,fragment).commit()
    }

    }
