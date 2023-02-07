package com.galvezssr.agendos.ui.create

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.galvezssr.agendos.kernel.Contact
import com.galvezssr.agendos.kernel.DbFirestore

class CreateContactViewModel: ViewModel() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    fun createContact(contact: Contact, usuarioActual: String, app: AppCompatActivity) {
        DbFirestore.createContact(contact, usuarioActual, app)
    }
}