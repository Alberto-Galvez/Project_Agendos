package com.galvezssr.agendos.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galvezssr.agendos.kernel.Contact
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    // Barra de progreso
    private val _estadoCarga = MutableLiveData(false)

    // Recycler del Fragment
    private val _listaResultante = MutableLiveData<List<Contact>>(emptyList())

    // Firestore BBDD
    private val bbdd = FirebaseFirestore.getInstance()

    private val listaContactos = mutableListOf<Contact>()

    ////////////////////////////////////////////////////
    // INCIALIZADOR ////////////////////////////////////
    ////////////////////////////////////////////////////

    init {

        /** Iniciamos el ViewModel. Su comportamiento es que los datos realmente seran modificados aqui,
         * mientras que el Fragment se encarga de escuchar cuando los datos se han modificado, para asi
         * aplicar los nuevos valores en el XML **/
        viewModelScope.launch {
            _estadoCarga.value = true

            bbdd.collection("contactos").get().addOnSuccessListener {
                for (document in it) {
                    val contacto = document.toObject(Contact::class.java)

                    contacto.nombre = document["nombre"].toString()
                    contacto.telefono = document["telefono"].toString()
                    contacto.email = document["email"].toString()
                    contacto.descripcion = document["descripcion"].toString()

                    listaContactos.add(contacto)
                }

                _listaResultante.postValue(listaContactos)
            }

            _estadoCarga.value = false
        }
    }

    ////////////////////////////////////////////////////
    // GETTERS /////////////////////////////////////////
    ////////////////////////////////////////////////////

    fun getEstadoCarga(): LiveData<Boolean> = _estadoCarga
    fun getListaResultante(): LiveData<List<Contact>> = _listaResultante
}
