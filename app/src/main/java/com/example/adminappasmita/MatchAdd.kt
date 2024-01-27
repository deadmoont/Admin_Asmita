package com.example.adminappasmita

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.adminappasmita.databinding.ActivityMainBinding
import com.example.adminappasmita.databinding.ActivityMatchAddBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class MatchAdd : AppCompatActivity() {
    private lateinit var binding: ActivityMatchAddBinding
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val spinnerOptions = listOf("Option 1", "Option 2", "Option 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.clg1.adapter = adapter
        binding.clg2.adapter = adapter

        binding.clg1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedOption = spinnerOptions[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.clg2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedOption = spinnerOptions[position]
//                saveDataToFirestore(selectedOption).
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing if nothing is selected
            }
        }

        // Add a listener for your EditText fields
        binding.matTxt.addTextChangedListener {
            val text1 = it.toString()
        }

        binding.time.addTextChangedListener {
            val text2 = it.toString()
        }

        binding.save.setOnClickListener {
            val selectedOption1 = binding.clg1.selectedItem.toString()
            val selectedOption2 = binding.clg2.selectedItem.toString()
            val editText1Value = binding.matTxt.text.toString()
            val editText2Value = binding.time.text.toString()

            if (selectedOption1.isNotEmpty() && selectedOption2.isNotEmpty()
                && editText1Value.isNotEmpty() && editText2Value.isNotEmpty()
            ) {
                saveDataToFirestore(selectedOption1, selectedOption2, editText1Value, editText2Value)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveDataToFirestore(selectedOption1: String, selectedOption2: String, editText1Value: String, editText2Value: String) {
        val collectionReference = firestore.collection("Match")

        val documentReference = collectionReference.document(UUID.randomUUID().toString())

        documentReference
            .set(
                mapOf(
                    "clg1" to selectedOption1,
                    "clg2" to selectedOption2,
                    "match" to editText1Value,
                    "time" to editText2Value
                )
            )
            .addOnSuccessListener {
                val snackbar = Snackbar.make(
                    findViewById(android.R.id.content),
                    "Added successfully",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}
