package com.galvezssr.agendos.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.galvezssr.agendos.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var email: String
    private lateinit var password: String

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup(binding)
    }

    private fun setup(binding: ActivityLoginBinding) {

        // BOTON DE ACCESO
        binding.botonAcceder.setOnClickListener {

            /** Lo mismo que lo anterior, solo que este es para acceder con email y
             * contraseñas existentes **/
            if (binding.campoEmail.text.isNotEmpty() && binding.campoPassword.text.isNotEmpty()) {

                email = binding.campoEmail.text.toString()
                password = binding.campoPassword.text.toString()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {

                        if (it.isSuccessful)
                            naviagteToMainActivity(email)
                        else
                            showAlert("Error", "El email o la contraseña son invalidos")

                    }

            } else {
                showEmptyToast()
            }
        }

        // BOTON DE REGISTRAR
        binding.botonRegistrar.setOnClickListener {

            navigateToRegisterActivity()

        }
    }

    private fun naviagteToMainActivity(email: String) {
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
        }

        startActivity(mainIntent)
    }

    private fun navigateToRegisterActivity() {
        val registerIntent = Intent(this, RegisterActivity::class.java).apply {}

        startActivity(registerIntent)
    }
}