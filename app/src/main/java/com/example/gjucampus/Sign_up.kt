package com.example.gjucampus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Sign_up : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var signup: AppCompatButton
    private lateinit var email: TextInputEditText
    private lateinit var pass: TextInputEditText
    private lateinit var confirm:TextInputEditText
    private lateinit var  already: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signup=findViewById(R.id.button)
        email=findViewById(R.id.emailEt)
        pass=findViewById(R.id.passET)
        confirm=findViewById(R.id.confirmPassEt)
        already=findViewById(R.id.textView)

        auth = FirebaseAuth.getInstance()

        already.setOnClickListener(){
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

        signup.setOnClickListener(){
            var email = email.text.toString()
            var pass = pass.text.toString()
            var confirm = confirm.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirm.isNotEmpty()) {
                if (pass == confirm) {

                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent= Intent(this,Login::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }


    }
}