package com.example.gjucampus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class Time_table_days : Fragment() {
    private lateinit var mondayButton: Button
    private lateinit var tuesdayButton: Button
    private lateinit var wednesdayButton: Button
    private lateinit var thursdayButton: Button
    private lateinit var fridayButton: Button

    private lateinit var selectedButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_time_table_days, container, false)

        mondayButton = view.findViewById(R.id.buttonMonday)
        tuesdayButton = view.findViewById(R.id.buttonTuesday)
        wednesdayButton = view.findViewById(R.id.buttonWednesday)
        thursdayButton = view.findViewById(R.id.buttonThursday)
        fridayButton = view.findViewById(R.id.buttonFriday)

        // Set the initial selected button
        selectedButton = mondayButton
        selectedButton.isSelected = true

        // Set click listeners for all buttons
        mondayButton.setOnClickListener { handleButtonClick(mondayButton) }
        tuesdayButton.setOnClickListener { handleButtonClick(tuesdayButton) }
        wednesdayButton.setOnClickListener { handleButtonClick(wednesdayButton) }
        thursdayButton.setOnClickListener { handleButtonClick(thursdayButton) }
        fridayButton.setOnClickListener { handleButtonClick(fridayButton) }

        return view
    }

    private fun handleButtonClick(button: Button) {
        if (button != selectedButton) {
            // Deselect the previously selected button
            selectedButton.isSelected = false

            // Select the newly clicked button
            button.isSelected = true
            selectedButton = button

            // Update the timetable based on the selected button
            val selectedDay = button.text.toString()
            val context = requireContext() // Get the context
            Toast.makeText(context, "day: $selectedDay", Toast.LENGTH_SHORT).show()

            // Find the Complete_day_timetable fragment using its ID and call updateTimetable
            val completeDayTimetableFragment = parentFragmentManager.findFragmentById(R.id.one_day_timetable) as Complete_day_timetable?
            completeDayTimetableFragment?.updateTimetable(selectedDay)
        }
    }

}

