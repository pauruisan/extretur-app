package com.uax.extretur.model

import java.io.Serializable

data class Forum (
    var uid: String? =null,
    var titulo: String ?=  null,
    var contenido: String ?= null,
    var fecha: String ?= null,
    var tema: String ?= null,
    var creador: String ?= null) : Serializable {
}