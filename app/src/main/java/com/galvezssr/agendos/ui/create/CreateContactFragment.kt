package com.galvezssr.agendos.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.galvezssr.agendos.databinding.FragmentCreateContactBinding
import com.galvezssr.agendos.kernel.Contact
import com.galvezssr.agendos.ui.showEmptyToast

class CreateContactFragment : Fragment() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var viewModel: CreateContactViewModel
    private lateinit var app: AppCompatActivity

    private lateinit var usuarioActual: String
    private var _binding: FragmentCreateContactBinding? = null
    private val binding get() = _binding!!

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreateContactBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CreateContactViewModel::class.java]
        app = (requireActivity() as AppCompatActivity)
        val root: View = binding.root

        /** Recibimos del intent del MainActivity el campo Email **/
        usuarioActual = app.intent.extras!!.getString("email")!!

        binding.boton.setOnClickListener {
            if (binding.campoNombre.text.isNotEmpty() && binding.campoTelefono.text.isNotEmpty()
                && binding.campoTelefono.text.isNotEmpty() && binding.campoDescripcion.text.isNotEmpty()) {

                val nombre = binding.campoNombre.text.toString()
                val telefono = binding.campoTelefono.text.toString()
                val email = binding.campoEmail.text.toString()
                val descripcion = binding.campoDescripcion.text.toString()

                val contact = Contact(nombre, telefono, email, descripcion)

                /** Llamamos al viewModel para crear el contacto sobre el usuario actual **/
                viewModel.createContact(contact, usuarioActual, app)

            } else
                app.showEmptyToast()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}