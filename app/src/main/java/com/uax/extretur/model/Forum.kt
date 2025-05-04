package com.uax.extretur.model

import java.io.Serializable

data class Forum (
    var uid: String,
    var titulo: String,
    var contenido: String,
    var fecha: String,
    var tema: String,
    var creador: String) : Serializable {
}