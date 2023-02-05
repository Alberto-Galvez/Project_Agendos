package com.galvezssr.agendos.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.galvezssr.agendos.databinding.FragmentCreateContactBinding
import com.galvezssr.agendos.ui.showAlert
import com.google.firebase.firestore.FirebaseFirestore

class CreateContactFragment : Fragment() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private var _binding: FragmentCreateContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var app: AppCompatActivity

    private lateinit var bbdd: FirebaseFirestore

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreateContactBinding.inflate(inflater, container, false)
        app = (requireActivity() as AppCompatActivity)
        bbdd = FirebaseFirestore.getInstance()

        val root: View = binding.root

        binding.boton.setOnClickListener {
            val nombre = binding.campoNombre.text.toString()
            val telefono = binding.campoTelefono.text.toString()
            val email = binding.campoEmail.text.toString()
            val descripcion = binding.campoDescripcion.text.toString()

            val contacto = hashMapOf(
                "nombre" to nombre,
                "telefono" to telefono,
                "email" to email,
                "descripcion" to descripcion
            )

            bbdd.collection("contactos").add(contacto).addOnCompleteListener {

                if (it.isSuccessful)
                    app.showAlert("Info", "Contacto añadido")
                else
                    app.showAlert("Error", "No se ha podido añadir el contacto")

            }


        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}