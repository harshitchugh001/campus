package com.example.gjucampus

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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var auth: FirebaseAuth
private lateinit var authId: String
private lateinit var name: TextView
private lateinit var course: TextView
private lateinit var database: DatabaseReference
//basic details
private lateinit var father_name: TextView
private lateinit var mother_name: TextView
private lateinit var address: TextView
private lateinit var contact_number: TextView
private lateinit var email: TextView
private lateinit var dob: TextView
private lateinit var gender: TextView
//teaching details
private lateinit var teachingIdValue: TextView
private lateinit var departmentValue: TextView
private lateinit var experienceValue: TextView

/**
 * A simple [Fragment] subclass.
 * Use the [Teacher_Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Teacher_Profile : Fragment() {
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
        val view= inflater.inflate(R.layout.fragment_teacher__profile, container, false)

        name = view.findViewById(R.id.student_name)
        course = view.findViewById(R.id.course)
        father_name = view.findViewById(R.id.father_value)
        mother_name = view.findViewById(R.id.mother_value)
        address = view.findViewById(R.id.address_value)
        contact_number = view.findViewById(R.id.contact_number_value)
        email = view.findViewById(R.id.email_value)
        dob = view.findViewById(R.id.dob_value)
        gender = view.findViewById(R.id.gender_value)

        //teaching details
        teachingIdValue = view.findViewById(R.id.teaching_id_value)
        departmentValue = view.findViewById(R.id.department_value)
        experienceValue = view.findViewById(R.id.experience_value)

        auth = FirebaseAuth.getInstance()
        authId = auth.currentUser?.uid.toString()

        readData(authId)
        Toast.makeText(requireContext(), "$authId", Toast.LENGTH_SHORT).show()


        return view;
    }

    private fun readData(authId: String) {
        database = FirebaseDatabase.getInstance().getReference("teacherprofile")
        database.child(authId).get().addOnSuccessListener {
            name.text = it.child("name").value.toString()
            course.text = it.child("department").value.toString()
            father_name.text = it.child("father_name").value.toString()
            mother_name.text = it.child("mother_name").value.toString()
            address.text = it.child("address").value.toString()
            contact_number.text = it.child("contact_number").value.toString()
            email.text = it.child("email").value.toString()
            dob.text = it.child("dob").value.toString()
            gender.text = it.child("gender").value.toString()

            teachingIdValue.text=it.child("teacherid").value.toString()
            departmentValue.text=it.child("department").value.toString()
            experienceValue.text=it.child("experience").value.toString()

            Toast.makeText(requireContext(), "Successfully Read", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener { exception ->

            Toast.makeText(requireContext(), "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Teacher_Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Teacher_Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
