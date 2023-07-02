package com.example.gjucampus

import OnedayAdapter
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar
import java.util.Locale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Complete_day_timetable : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var timetableRecyclerView: RecyclerView
    private lateinit var timetableAdapter: OnedayAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var authId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_complete_day_timetable, container, false)
        timetableRecyclerView = view.findViewById(R.id.timetableRecyclerView)
        timetableRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        timetableAdapter = OnedayAdapter(mutableListOf())
        timetableRecyclerView.adapter = timetableAdapter
        val today = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).toLowerCase()

        auth = FirebaseAuth.getInstance()
        authId= auth.currentUser?.uid.toString()
        fetchDetails(authId,today)

        return view
    }

    fun updateTimetable(selectedDay: String) {
        Toast.makeText(context, "update called", Toast.LENGTH_SHORT).show()

        auth = FirebaseAuth.getInstance()
        authId = auth.currentUser?.uid.toString()
        fetchDetails(authId, selectedDay)
    }

    private fun fetchDetails(authId: String, selectedDay: String) {
        database = FirebaseDatabase.getInstance().getReference("studentprofile")
        database.child(authId).get().addOnSuccessListener { dataSnapshot ->
            val course = dataSnapshot.child("course").value.toString()
            val department = dataSnapshot.child("department").value.toString()
            val year = dataSnapshot.child("year").value.toString()

            // Call fetchTimetableData with the retrieved details
            fetchTimetableData(selectedDay, course, department, year, requireContext())
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to fetch user details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchTimetableData(
        selectedDay: String,
        course: String,
        department: String,
        year: String,
        context: Context
    ) {
        val lowercaseSelectedDay = selectedDay.toLowerCase()
        val databaseReference = FirebaseDatabase.getInstance().getReference("students")
            .child(course)
            .child(department)
            .child(year)
            .child("timetable")
            .child(lowercaseSelectedDay)

        databaseReference.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val timetableList = mutableListOf<TimetableItem>()

                for (timeSnapshot in dataSnapshot.children) {
                    val time = timeSnapshot.child("time").value.toString()
                    val room = timeSnapshot.child("room").value.toString()
                    val subject = timeSnapshot.child("subject").value.toString()
                    val teacher = timeSnapshot.child("teacher").value.toString()
                    val type = timeSnapshot.child("type").value.toString()
                    Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()

                    val timetableItem = TimetableItem(time, room, subject, teacher, type)
                    timetableList.add(timetableItem)
                }


                // Update the timetable adapter with the fetched data
                timetableAdapter.setData(timetableList)
            } else {
                Toast.makeText(context, "Data does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to fetch timetable data", Toast.LENGTH_SHORT).show()
        }
    }
}
