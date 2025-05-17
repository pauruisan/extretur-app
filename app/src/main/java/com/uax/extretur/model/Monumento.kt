package com.uax.extretur.model

import java.io.Serializable

data class Monumento (var nombre: String, var descripcion: String, var poblacion:String, var provincia: String, var imagen: String,
    val latitud: Double, val longitud: Double) : Serializable