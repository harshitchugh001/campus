package com.example.gjucampus

import TimetableAdapter
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar
import java.util.Locale


data class TimetableItem(
    val time: String?,
    val room: String?,
    val subject: String?,
    val teacher: String?,
    val type: String?
)

private lateinit var timetableRecyclerView: RecyclerView
private lateinit var timetableAdapter: TimetableAdapter
private lateinit var database : DatabaseReference
private lateinit var auth: FirebaseAuth

private lateinit var authId: String
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Today_timetable.newInstance] factory method to
 * create an instance of this fragment.
 */
class Today_timetable : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_today_timetable, container, false)

        timetableRecyclerView = view.findViewById(R.id.timetableRecyclerView)
//        Toast.makeText(requireContext(), "Hello, World!", Toast.LENGTH_SHORT).show()

        // Set up the RecyclerView with LinearLayoutManager
        timetableRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Create an instance of the TimetableAdapter
        timetableAdapter = TimetableAdapter(mutableListOf())

        // Set the adapter for the RecyclerView
        timetableRecyclerView.adapter = timetableAdapter
        val today = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).toLowerCase()

        // Fetch the timetable data from Firebase
        auth = FirebaseAuth.getInstance()
        authId= auth.currentUser?.uid.toString()
        fetchdetails(authId,today)

//        Toast.makeText(requireContext(), "Hello $today", Toast.LENGTH_SHORT).show()



        return view
    }

    private fun fetchdetails(authId:String , today: String) {

            database = FirebaseDatabase.getInstance().getReference("studentprofile")
            database.child(authId).get().addOnSuccessListener {

               val course=it.child("course").value.toString()


                val department=it.child("department").value.toString()
               val year=it.child("year").value.toString()
//                Toast.makeText(context, "Data $course,$department,$year", Toast.LENGTH_SHORT).show()

                fetchTimetableData(today,course,department,year,requireContext())

//            Toast.makeText(this, "Successfuly Read", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
//            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun fetchTimetableData(today: String,course:String,department:String,year:String,context: Context) {
        Toast.makeText(context, "Data $today", Toast.LENGTH_SHORT).show()
        val databaseReference = FirebaseDatabase.getInstance().getReference("students")
            .child(course)
            .child(department)
            .child(year)
            .child("timetable")
            .child(today)

        databaseReference.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val timetableList = mutableListOf<TimetableItem>()

                for (timeSnapshot in dataSnapshot.children) {
                    val time = timeSnapshot.child("time").value.toString()
                    val room = timeSnapshot.child("room").value.toString()
                    val subject = timeSnapshot.child("subject").value.toString()
                    val teacher = timeSnapshot.child("teacher").value.toString()
                    val type = timeSnapshot.child("type").value.toString()

                    val timetableItem = TimetableItem(time, room, subject, teacher, type)
                    timetableList.add(timetableItem)
                }

                timetableAdapter.setData(timetableList)
            } else {
                Toast.makeText(context, "Data does not exist", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to fetch timetable data", Toast.LENGTH_SHORT).show()
        }
    }





