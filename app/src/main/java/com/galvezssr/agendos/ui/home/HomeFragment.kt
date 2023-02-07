package com.galvezssr.agendos.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.galvezssr.agendos.databinding.FragmentListBinding
import com.galvezssr.agendos.kernel.ContactAdapter

class HomeFragment : Fragment() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var adapter: ContactAdapter
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
        adapter = ContactAdapter(emptyList())
        binding.recycler.adapter = adapter

        /** Observamos los cambios que se producen en el ViewModel esperando para ser modificados en los XML **/
        viewModel.getListaResultante().observe(viewLifecycleOwner) {
            adapter.contactos = it
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}