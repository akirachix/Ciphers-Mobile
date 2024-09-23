package com.akirachix.totosteps
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ResourcesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_resources, container, false)

        val assessmentResources = view.findViewById<TextView>(R.id.assessmentResources)
        val milestoneResources = view.findViewById<TextView>(R.id.milestoneResources)

        assessmentResources.setOnClickListener {
            startActivity(Intent(activity, AssessmentResourcesAllActivity::class.java))
        }

        milestoneResources.setOnClickListener {
            startActivity(Intent(activity, MilestonesResourcesAllActivity::class.java))
        }

        return view
    }
}