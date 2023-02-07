package com.galvezssr.agendos.kernel

import androidx.appcompat.app.AppCompatActivity
import com.galvezssr.agendos.ui.showAlert
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

object DbFirestore {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private const val USER_COLECCTION = "usuarios"
    private const val CONTACT_COLECCTION = "contactos"

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private fun getBBDD(): FirebaseFirestore = FirebaseFirestore.getInstance()

    /** Obtenemos todos los contactos del usuario actual **/
    suspend fun getContactsFromUser(email: String): List<Contact> {
        val bbdd = getBBDD()
        val instancia = bbdd.collection(USER_COLECCTION).document(email).collection(CONTACT_COLECCTION).get().await()
        val contactos = mutableListOf<Contact>()

        for (documento in instancia) {
            val contacto = documento.toObject(Contact::class.java)
            contactos.add(contacto)
        }

        return contactos
    }

    // Crear un objeto usuario para poder añadirse a la BBDD
    fun createUser(user: User, app: AppCompatActivity) {
        val bbdd = getBBDD()
        val userMap = hashMapOf(
            "nombre" to user.nombre,
            "apellidos" to user.apellidos,
            "telefono" to user.telefono,
            "email" to user.email,
            "password" to user.password
        )

        bbdd.collection(USER_COLECCTION).document(user.email).set(userMap).addOnCompleteListener{

            /** Si el creado del usuario en la BBDD es exitoso... **/
            if (it.isSuccessful)
                app.showAlert("Info", "Usuario creado correctamente")
            else
                app.showAlert("Error", "Se ha producido un error al añadir el usuario a la BBDD")
        }
    }

    fun createContact(contact: Contact, usuarioActual: String, app: AppCompatActivity) {
        val bbdd = getBBDD()
        val contactMap = hashMapOf(
            "nombre" to contact.nombre,
            "telefono" to contact.telefono,
            "email" to contact.email,
            "descripcion" to contact.descripcion
        )

        bbdd.collection(USER_COLECCTION).document(usuarioActual).collection(CONTACT_COLECCTION).document(contact.telefono)
            .set(contactMap).addOnCompleteListener {

                /** Si el creado del contacto en la BBDD es exitoso... **/
                if (it.isSuccessful)
                    app.showAlert("Info", "Contacto añadido al correo: $usuarioActual")
                else
                    app.showAlert("Error", "No se ha podido añadir el contacto")
            }
    }

    fun getFlow(usuarioActual: String): Flow<List<Contact>> {
        return FirebaseFirestore.getInstance()
            .collection(USER_COLECCTION).document(usuarioActual).collection(CONTACT_COLECCTION)
            .orderBy("nombre", Query.Direction.DESCENDING).snapshots().map {
                it.toObjects(Contact::class.java)
            }
    }

}