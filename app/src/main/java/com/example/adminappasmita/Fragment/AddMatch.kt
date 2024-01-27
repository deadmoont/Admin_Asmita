package com.example.adminappasmita.Fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.adminappasmita.Adapter.CustomSpinnerAdapter
import com.example.adminappasmita.DataClass.IIIT_Options
import com.example.adminappasmita.R
import com.example.adminappasmita.databinding.FragmentAddMatchBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class AddMatch : Fragment() {
    private lateinit var binding: FragmentAddMatchBinding
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var calendar: Calendar
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMatchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchOptionsFromFirestore(requireContext())
        binding.clg1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    binding.date.text = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                },
                year, month, day
            )

            // Show the DatePickerDialog
            datePickerDialog.show()
        }
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)

        // Set an onClickListener for the button
        binding.Time.setOnClickListener {
            // Create a TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _: TimePicker?, selectedHour: Int, selectedMinute: Int ->
                    // Update the selected time in the button
                    val formattedHour = if (selectedHour > 12) selectedHour - 12 else selectedHour
                    val amPm = if (selectedHour >= 12) "PM" else "AM"
                    binding.Time.text = String.format(Locale.getDefault(), "%02d:%02d %s", formattedHour, selectedMinute, amPm)
                },
                hour, minute, false // Set is24HourView to false for 12-hour format
            )

            // Show the TimePickerDialog
            timePickerDialog.show()
        }
        binding.clg2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.matTxt.addTextChangedListener {
            val text1 = it.toString()
        }

        binding.Time.addTextChangedListener {
            val text2 = it.toString()
        }

        binding.save.setOnClickListener {
            val clgName1 = binding.clg1.selectedItem as IIIT_Options
            val clgName2 = binding.clg2.selectedItem as IIIT_Options
            val editText1Value = binding.matTxt.text.toString()
            val editText2Value = binding.Time.text.toString()

            if (editText1Value.isNotEmpty() && editText2Value.isNotEmpty()) {
                saveDataToFirestore(
                    clgName1, clgName2, editText1Value, editText2Value
                )
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun fetchOptionsFromFirestore(context: Context) {
        val collectionReference = firestore.collection("IIITS")
        collectionReference.orderBy("Name").get()
            .addOnSuccessListener { result ->
                val optionsList = mutableListOf<IIIT_Options>()
                for (document in result) {
                    val name = document.getString("Name") ?: ""
                    val imageUrl = document.getString("logo") ?: ""
                    val data = IIIT_Options(name, imageUrl)
                    optionsList.add(data)
                }
                val adapter =
                    CustomSpinnerAdapter(context, R.layout.custom_spinner_item, optionsList)
                binding.clg1.adapter = adapter
                binding.clg2.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    requireContext(), exception.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun saveDataToFirestore(
        clgName1: IIIT_Options,
        clgName2: IIIT_Options,
        editText1Value: String,
        editText2Value: String
    ) {
        val collectionReference = firestore.collection("Match")

        val documentReference = collectionReference.document(UUID.randomUUID().toString())
        documentReference.set(
            mapOf(
                "clg1" to clgName1.name,
                "clg2" to clgName2.name,
                "img1" to clgName1.img,
                "img2" to clgName2.img,
                "match" to editText1Value,
                "time" to editText2Value
            )
        ).addOnSuccessListener {
            val snackbar = Snackbar.make(
                binding.root, "Added successfully", Snackbar.LENGTH_SHORT
            )
            snackbar.show()
        }.addOnFailureListener { e ->
            Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}