package com.jacobshinta.weatherapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LocationPromptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_prompt)

        val locationEditText = findViewById<EditText>(R.id.etLocation)
        setupLocationEditText(locationEditText)
    }

    private fun setupLocationEditText(locationEditText: EditText) {
        locationEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handleLocationInput(locationEditText)
                true
            } else {
                false
            }
        }
    }

    private fun handleLocationInput(locationEditText: EditText) {
        val location = locationEditText.text.toString()
        if (location.isNotBlank()) {
            saveLocation(location)
            startMainActivity(location)
            hideKeyboard(locationEditText)
            finish()
        }
    }

    private fun saveLocation(location: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("WeatherApp", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("HasUsedApp", true)
        editor.putString("UserLocation", location)
        editor.apply()
    }

    private fun startMainActivity(location: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("UserLocation", location)
        startActivity(intent)
    }

    private fun hideKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}
