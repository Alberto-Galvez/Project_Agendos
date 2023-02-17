package com.galvezssr.agendos.kernel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Contact(
    var nombre: String,
    var telefono: String,
    var email: String,
    var descripcion: String): Parcelable {
    constructor(): this("", "", "", "")

}

