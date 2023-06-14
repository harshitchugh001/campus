package com.example.gjucampus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.EmailAuthProvider

class Password_change : Fragment() {

    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnChangePassword: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_password_change, container, false)

        etCurrentPassword = view.findViewById(R.id.current_pass)
        etNewPassword = view.findViewById(R.id.update_pass)
        etConfirmPassword = view.findViewById(R.id.confirmPassEt)
        btnChangePassword = view.findViewById(R.id.button)

        btnChangePassword.setOnClickListener {
            val currentPassword = etCurrentPassword.text.toString()
            val newPassword = etNewPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (newPassword == confirmPassword) {
                changePassword(currentPassword, newPassword)
            } else {
                showToast("New password and confirm password do not match")
            }
        }

        return view
    }

    private fun changePassword(currentPassword: String, newPassword: String) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val credential = user?.email?.let {
            EmailAuthProvider.getCredential(it, currentPassword)
        }

        if (credential != null) {
            user?.reauthenticate(credential)?.addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            showToast("Password updated successfully")
                        } else {
                            val exception = updateTask.exception as? FirebaseAuthException
                            showToast("Password update failed: ${exception?.message}")
                        }
                    }
                } else {
                    showToast("Reauthentication failed")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
