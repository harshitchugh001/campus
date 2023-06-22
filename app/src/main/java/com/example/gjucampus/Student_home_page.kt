package com.example.gjucampus

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Student_home_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class Student_home_page : Fragment() {
    private lateinit var announcement:CardView
    private lateinit var timetable:CardView
    private lateinit var result:CardView
    private lateinit var assignment:CardView
    private lateinit var library:CardView
    private lateinit var attendance:CardView
    private lateinit var exam:CardView


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
        var view= inflater.inflate(R.layout.fragment_student_home_page, container, false)
        announcement=view.findViewById(R.id.announcement)
        result=view.findViewById(R.id.result)
        library=view.findViewById(R.id.library)
        attendance=view.findViewById(R.id.attendance)
        timetable=view.findViewById(R.id.timetable)
        assignment=view.findViewById(R.id.assignment)
        exam=view.findViewById(R.id.exam)

        announcement.setOnClickListener(){
            val intent = Intent(requireActivity(), Announcement::class.java)
            startActivity(intent)
        }
        assignment.setOnClickListener(){
            val intent = Intent(requireActivity(), Assignment::class.java)
            startActivity(intent)
        }
        exam.setOnClickListener(){
            val intent = Intent(requireActivity(), Exam::class.java)
            startActivity(intent)
        }
        result.setOnClickListener(){
            val intent = Intent(requireActivity(), Result::class.java)
            startActivity(intent)
        }
        library.setOnClickListener(){
            val intent = Intent(requireActivity(), Library::class.java)
            startActivity(intent)
        }
        attendance.setOnClickListener(){
            val intent = Intent(requireActivity(), Attendance::class.java)
            startActivity(intent)
        }
        timetable.setOnClickListener() {
            val intent = Intent(requireActivity(), Time_table::class.java)
            startActivity(intent)
        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Student_home_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Student_home_page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}