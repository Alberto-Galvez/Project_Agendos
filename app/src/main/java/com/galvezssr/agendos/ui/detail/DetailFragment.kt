package com.galvezssr.agendos.ui.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.galvezssr.agendos.R
import com.galvezssr.agendos.databinding.FragmentDetailBinding
import com.galvezssr.agendos.kernel.Contact
import com.galvezssr.agendos.kernel.DbFirestore

@Suppress("DEPRECATION")
class DetailFragment: Fragment(R.layout.fragment_detail) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    companion object { const val CONTACTO_SELECCIONADO = "contactoSeleccionado" }

    private lateinit var contact: Contact
    private lateinit var app: AppCompatActivity

    private val usuarioActual = DbFirestore.getUsuarioActual()
    private val viewModel: DetailFragmentViewModel by viewModels { DetailFragmentViewModelFactory(arguments?.getParcelable(CONTACTO_SELECCIONADO)!!) }

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        app = (requireActivity() as AppCompatActivity)

        /** Esperamos hasta que el contacto se modifique en el viewModel **/
        viewModel.getContacto().observe(viewLifecycleOwner) {
            contact = it

            /** Establecemos el titulo de la barra de navegacion **/
            app.supportActionBar!!.title = "Contacto"

            binding.campoNombre.text = "-  " + contact.nombre
            binding.campoTelefono.text = "-  " + contact.telefono
            binding.campoEmail.text = "-  " + contact.email
            binding.campoDescripcion.text = "-  " + contact.descripcion
        }

        /** Establecemos un escuchador de eventos para el boton de correo electronico **/
        binding.botonEmail.setOnClickListener {

            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + contact.email))
            startActivity(intent)
        }

        /** Establecemos un escuchador de eventos para el boton de llamar **/
        binding.botonLlamar.setOnClickListener {

            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.telefono))
            startActivity(intent)
        }

        /** Establecemos el escuchador de eventos para el boton de borrar contacto **/
        binding.botonBorrar.setOnClickListener {

            /** Creamos una Alerta personalizada que confirma si queremos borrar al contacto **/
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Advertencia")
            builder.setMessage("¿Deseas borrar al contacto: ${contact.nombre}?")

            // Configuramos el botón positivo del diálogo
            builder.setPositiveButton("Sí") { _, _ ->
                viewModel.borrarContacto(contact, usuarioActual, app)
            }

            // Configuramos el botón negativo del diálogo
            builder.setNegativeButton("No") { _, _ ->

            }

            // Mostramos el diálogo
            builder.create().show()
        }
    }
}