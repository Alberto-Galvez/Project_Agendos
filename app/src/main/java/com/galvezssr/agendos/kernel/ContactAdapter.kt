package com.galvezssr.agendos.kernel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galvezssr.agendos.R

class ContactAdapter(var contactos: List<Contact>): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = contactos.size

    override fun onBindViewHolder(holder: ViewHolder, posicion: Int) {
        val contacto = contactos[posicion]

        holder.nombre.text = contacto.nombre
        holder.telefono.text = contacto.telefono
//      holder.imagen
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
//      val imagen = view.findViewById<ImageView>(R.id.imagen)
        val nombre = view.findViewById<TextView>(R.id.nombre)
        val telefono = view.findViewById<TextView>(R.id.telefono)


    }
}

