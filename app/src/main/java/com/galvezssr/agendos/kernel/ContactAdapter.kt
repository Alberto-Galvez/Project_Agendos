package com.galvezssr.agendos.kernel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galvezssr.agendos.R

class ContactAdapter(var contactos: List<Contact>): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = contactos.size

    override fun onBindViewHolder(holder: ViewHolder, posicion: Int) {
        val contacto = contactos[posicion]

        holder.nombre.text = contacto.nombre
        holder.telefono.text = contacto.telefono

    }

    ////////////////////////////////////////////////////
    // MINI-CLASES /////////////////////////////////////
    ////////////////////////////////////////////////////
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val nombre: TextView = view.findViewById(R.id.nombre)
        val telefono: TextView = view.findViewById(R.id.telefono)


    }
}

