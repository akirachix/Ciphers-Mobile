package com.akirachix.totosteps.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityChildAccountBinding
import java.util.Calendar

class ChildAccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityChildAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupUI()
    }

    fun setupUI() {
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }

        binding.dateOfBirthInput.setOnClickListener {
            showDatePicker()
        }

        binding.dateOfBirthLayout.setEndIconOnClickListener {
            showDatePicker()
        }

        binding.saveButton.setOnClickListener {

            var intent = Intent(this, AutismUploadPhoto::class.java)
            startActivity(intent)
            saveChildData()
        }
    }

    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.dateOfBirthInput.setText(selectedDate)
        }, year, month, day).show()
    }

    fun saveChildData() {
        val username = binding.usernameInput.text.toString()
        val dateOfBirth = binding.dateOfBirthInput.text.toString()

        if (username.isEmpty() || dateOfBirth.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: Implement the logic to save the child's data
        Toast.makeText(this, "Child data saved successfully", Toast.LENGTH_SHORT).show()
    }
}
