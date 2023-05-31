package com.example.gjucampus

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String
    private lateinit var name:TextView
    private lateinit var course:TextView
    private lateinit var database : DatabaseReference
    //basic details
    private lateinit var father_name:TextView
    private lateinit var mother_name:TextView
    private lateinit var address:TextView
    private lateinit var contact_number:TextView
    private lateinit var email:TextView
    private lateinit var dob:TextView
    private lateinit var gender:TextView
    //academic details
    private lateinit var course_value:TextView
    private lateinit var roll_no:TextView
    private lateinit var department:TextView
    private lateinit var year:TextView
    //hostel details
    private lateinit var hostel_name:TextView
    private lateinit var room_no:TextView


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
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        name = view.findViewById(R.id.student_name)
        course = view.findViewById(R.id.course)
        father_name = view.findViewById(R.id.father_value)
        mother_name = view.findViewById(R.id.mother_value)
        address = view.findViewById(R.id.address_value)
        contact_number = view.findViewById(R.id.contact_number_value)
        email = view.findViewById(R.id.email_value)
        dob = view.findViewById(R.id.dob_value)
        gender = view.findViewById(R.id.gender_value)

        course_value=view.findViewById(R.id.course_value)
        roll_no=view.findViewById(R.id.roll_no_value)
        department=view.findViewById(R.id.Department_value)
        year=view.findViewById(R.id.year_value)

        hostel_name=view.findViewById(R.id.hostel_name_value)
        room_no=view.findViewById(R.id.room_no_value)

        auth = FirebaseAuth.getInstance()
        authId= auth.currentUser?.uid.toString()
        
        
        readData(authId)



        return view;

    }

    private fun readData(authId: String) {

        database = FirebaseDatabase.getInstance().getReference("studentprofile")
        database.child(authId).get().addOnSuccessListener {
            name.text=it.child("name").value.toString()
            course.text=it.child("course").value.toString()
            father_name.text=it.child("father_name").value.toString()
            mother_name.text=it.child("mother_name").value.toString()
            address.text=it.child("address").value.toString()
            contact_number.text=it.child("contact_number").value.toString()
            email.text=it.child("email").value.toString()
            dob.text=it.child("dob").value.toString()
            gender.text=it.child("gender").value.toString()
            course_value.text=it.child("course").value.toString()
            roll_no.text=it.child("roll_no").value.toString()
            department.text=it.child("department").value.toString()
            year.text=it.child("year").value.toString()
            hostel_name.text=it.child("hostel_name").value.toString()
            room_no.text=it.child("room_no").value.toString()
//            Toast.makeText(this, "Successfuly Read", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
//            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {


        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}