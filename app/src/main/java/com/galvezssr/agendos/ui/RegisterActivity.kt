package com.galvezssr.agendos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.galvezssr.agendos.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity: AppCompatActivity() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var name: String
    private lateinit var lastname: String
    private lateinit var phone: String
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var bbdd: FirebaseFirestore

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup(binding)
    }

    private fun setup(binding: ActivityRegisterBinding) {
        bbdd = FirebaseFirestore.getInstance()

        binding.botonRegistrar.setOnClickListener {

            if (binding.campoNombre.text.isNotEmpty() && binding.campoApellidos.text.isNotEmpty()
                && binding.campoTelefono.text.isNotEmpty() && binding.campoEmail.text.isNotEmpty()
                && binding.campoPassword.text.isNotEmpty()) {

                name = binding.campoNombre.text.toString()
                lastname = binding.campoApellidos.text.toString()
                phone = binding.campoTelefono.text.toString()
                email = binding.campoEmail.text.toString()
                password = binding.campoPassword.text.toString()

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    if (it.isSuccessful) {
                        val usuario = hashMapOf(
                            "nombre" to name,
                            "apellidos" to lastname,
                            "telefono" to phone,
                            "email" to email,
                            "password" to password
                        )

                        bbdd.collection("usuarios").add(usuario)

                        showAlert("Info", "Usuario creado correctamente")
                    } else
                        showAlert("Error", "Ya existe un usuario con el mismo email")
                }

            } else
                showEmptyToast()

        }
    }
}