package com.uax.extretur.model


import com.google.firebase.Timestamp
import java.io.Serializable

data class Comentario (
    val creador: String,
    val contenido: String,
    val fecha: Timestamp = Timestamp.now()
) : Serializable {
    constructor() : this("", "", Timestamp.now())
}