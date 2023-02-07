package com.galvezssr.agendos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.galvezssr.agendos.databinding.ActivityRegisterBinding
import com.galvezssr.agendos.kernel.DbFirestore
import com.galvezssr.agendos.kernel.User
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity: AppCompatActivity() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var name: String
    private lateinit var lastname: String
    private lateinit var phone: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var user: User

    private val auth = FirebaseAuth.getInstance()

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

        binding.botonRegistrar.setOnClickListener {

            if (binding.campoNombre.text.isNotEmpty() && binding.campoApellidos.text.isNotEmpty()
                && binding.campoTelefono.text.isNotEmpty() && binding.campoEmail.text.isNotEmpty()
                && binding.campoPassword.text.isNotEmpty()) {

                name = binding.campoNombre.text.toString()
                lastname = binding.campoApellidos.text.toString()
                phone = binding.campoTelefono.text.toString()
                email = binding.campoEmail.text.toString()
                password = binding.campoPassword.text.toString()

                user = User(name, lastname, phone, email, password)

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    /** Si no existe un usuario en la BBDD con ese email y contraseña... **/
                    if (it.isSuccessful) {
                        DbFirestore.createUser(user, this)

                    } else
                        showAlert("Error", "Ya existe un usuario con el mismo email")
                }

            } else
                showEmptyToast()

        }
    }
}