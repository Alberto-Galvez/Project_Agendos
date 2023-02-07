package com.galvezssr.agendos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.galvezssr.agendos.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var email: String
    private lateinit var password: String

    private val auth = FirebaseAuth.getInstance()

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

            /** Entrara en la condicion solo si los campos no estan vacios **/
            if (binding.campoEmail.text.isNotEmpty() && binding.campoPassword.text.isNotEmpty()) {

                email = binding.campoEmail.text.toString()
                password = binding.campoPassword.text.toString()

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

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