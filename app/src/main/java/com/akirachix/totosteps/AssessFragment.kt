package com.akirachix.totosteps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.akirachix.totosteps.activity.DevelopmentalMilestonesScreenOne
import com.akirachix.totosteps.activity.ParentsAgreementActivity



class AssessFragment : Fragment() {


    lateinit var radioGroup: RadioGroup
    lateinit var rbImageUpload: RadioButton
    lateinit var rbQuestionnaire: RadioButton
    lateinit var btnProceed: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_assess, container, false)

        radioGroup = view.findViewById(R.id.rgOptions)
        rbImageUpload = view.findViewById(R.id.rbImageUpload)
        rbQuestionnaire = view.findViewById(R.id.rbQuestionnaire)
        btnProceed = view.findViewById(R.id.btnProceed)


        btnProceed.setOnClickListener {

            val selectedOptionId = radioGroup.checkedRadioButtonId

            if (selectedOptionId == R.id.rbImageUpload) {
                navigateToImageUpload()
            } else if (selectedOptionId == R.id.rbQuestionnaire) {
                navigateToQuestionnaire()
            } else {
                Toast.makeText(requireContext(), "Please choose an option", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun navigateToImageUpload() {
        val intent = Intent(requireContext(), ParentsAgreementActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToQuestionnaire() {
        val intent = Intent(requireContext(), DevelopmentalMilestonesScreenOne::class.java)
        startActivity(intent)
    }
}