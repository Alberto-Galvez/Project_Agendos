package com.galvezssr.agendos.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galvezssr.agendos.kernel.Contact
import com.galvezssr.agendos.kernel.DbFirestore

class DetailFragmentViewModel(val contact: Contact): ViewModel() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private val _contacto = MutableLiveData(contact)

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    fun borrarContacto(contact: Contact, usuarioActual: String, app: AppCompatActivity) {
        DbFirestore.deleteContact(contact, usuarioActual, app)
    }

    fun getContacto(): LiveData<Contact> = _contacto

}

@Suppress("UNCHECKED_CAST")
class DetailFragmentViewModelFactory(private val contact: Contact): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailFragmentViewModel(contact) as T
    }
}