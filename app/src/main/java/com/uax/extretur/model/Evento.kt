package com.uax.extretur.model

import java.time.LocalDate

data class Evento (
    val fecha: LocalDate,
    val titulo: String,
    val localizacion: String
)