package com.akirachix.totosteps
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.akirachix.totosteps.databinding.FragmentResourcesBinding

class ResourcesFragment : Fragment() {

    private var _binding: FragmentResourcesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResourcesBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.assessmentCardView.setOnClickListener {
            startActivity(Intent(activity, AssessmentResourcesAllActivity::class.java))
        }

        binding.milestoneCardView.setOnClickListener {
            startActivity(Intent(activity, MilestonesResourcesAllActivity::class.java))
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}