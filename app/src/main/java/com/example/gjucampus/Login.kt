package com.example.gjucampus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var login: AppCompatButton
    private lateinit var email1: TextInputEditText
    private lateinit var pass1: TextInputEditText
    private lateinit var forget: TextView
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login = findViewById(R.id.login)
        email1 = findViewById(R.id.email)
        pass1 = findViewById(R.id.password)
        auth = FirebaseAuth.getInstance()
        forget = findViewById(R.id.forget)

        forget.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }
        login.setOnClickListener() {
            val email = email1.text.toString()
            val username = email.substringBefore("@")
            val pass = pass1.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
//                        Toast.makeText(this,"arrived $username",Toast.LENGTH_SHORT).show()
                        readData(username) { userData ->
//                            Toast.makeText(this,"arrived $userData",Toast.LENGTH_SHORT).show()
                            if (userData =="admin") {
                                val intent = Intent(this, Admin_work::class.java)
                                startActivity(intent)
                            }
                            else if(userData=="security") {
                                val intent = Intent(this, Security_work::class.java)
                                startActivity(intent)
                            } else if(userData=="professor") {
                                val intent = Intent(this, Professors_work::class.java)
                                startActivity(intent)
                            } else if(userData=="student") {
                                val intent = Intent(this, Student_work::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "Invalid user", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, signInTask.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun readData(username: String, callback: (String?) -> Unit) {
         database= FirebaseDatabase.getInstance().getReference("users")
        val userRef: DatabaseReference = database.child(username)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userData: String? = dataSnapshot.value as? String
                callback(userData)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                callback(null)
            }
        })
    }
}


