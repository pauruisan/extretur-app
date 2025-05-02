package com.uax.extretur.model

import java.io.Serializable

class Forum (
    var id: String = "",
    var titulo: String,
    var contenido: String,
    var fecha: String,
    var tema: String,
    var creador: String) : Serializable {
}