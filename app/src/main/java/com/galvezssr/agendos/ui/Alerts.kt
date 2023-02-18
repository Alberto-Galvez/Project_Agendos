package com.galvezssr.agendos.ui

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/** Este archivo contiene funciones extras que extienden de la clase que le indiquemos al principio **/

fun AppCompatActivity.showEmptyToast() {
    Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showAlert(code: String, text: String) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(code)
    builder.setMessage(text)
    builder.setPositiveButton("Aceptar", null)

    builder.create().show()
}