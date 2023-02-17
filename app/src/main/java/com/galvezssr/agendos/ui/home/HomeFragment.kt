package com.galvezssr.agendos.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.galvezssr.agendos.R
import com.galvezssr.agendos.databinding.FragmentListBinding
import com.galvezssr.agendos.kernel.Contact
import com.galvezssr.agendos.kernel.ContactAdapter
import com.galvezssr.agendos.ui.detailFragment.DetailFragment

class HomeFragment : Fragment() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var app: AppCompatActivity

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        app = (requireActivity() as AppCompatActivity)

        /** Recibimos del intent del MainActivity el campo Email **/
        val usuarioActual = app.intent.extras!!.getString("email")

        /** Incializamos el viewModel y el binding **/
        val viewModel: HomeViewModel by viewModels { HomeViewModelFactory(usuarioActual!!) }
        _binding = FragmentListBinding.inflate(inflater, container, false)

        /** Inicializamos el recycler y el adapter **/
        val adapter = ContactAdapter {contacto -> navigateTo(contacto)}
        binding.recycler.adapter = adapter

        /** Observamos los cambios que se producen en el ViewModel esperando para ser modificados en los XML **/
        viewModel.getListaResultante().observe(viewLifecycleOwner) {
            adapter.contactos = it
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    /** Esta funcion navegara hacia el DetailFragment cuando sea llamada, recibe por parametro un contacto,
     * que establecera en la variable estatica de la clase DetailFragment **/
    fun navigateTo(contact: Contact) {
        findNavController().navigate(
            R.id.action_nav_home_to_detailFragment, bundleOf(DetailFragment.CONTACTO_SELECCIONADO to contact)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}