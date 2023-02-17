package com.galvezssr.agendos.kernel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galvezssr.agendos.R

class ContactAdapter(val listener: (Contact) -> Unit): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    /** Ahora, en vez de que el adapter reciba una lista de contactos vacia, tiene como parametro un contacto,
     * que sera el listener de cada contacto, mientras que la lista vacia la inicializaremos en el propio adapter **/
    var contactos = emptyList<Contact>()

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

        /** Aqui creamos un evento de escucha por cada contacto, en el que si se pulsa, enviara al HomeFragment
         * el contacto que se ha pulsado, y alli, navegaremos hacia al DetailFragment **/
        holder.itemView.setOnClickListener {
            listener(contacto)
        }

    }

    ////////////////////////////////////////////////////
    // MINI-CLASES /////////////////////////////////////
    ////////////////////////////////////////////////////
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val nombre: TextView = view.findViewById(R.id.nombre)
        val telefono: TextView = view.findViewById(R.id.telefono)


    }
}

