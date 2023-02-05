package com.galvezssr.agendos.kernel

data class Contact(
    var nombre: String,
    var telefono: String,
    var email: String,
    var descripcion: String) {
    constructor(): this("", "", "", "")
}