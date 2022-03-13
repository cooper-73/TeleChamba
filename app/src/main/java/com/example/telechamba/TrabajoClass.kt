package com.example.telechamba

data class TrabajoClass(
    val nombre: String,
    val ruc: String,
    val vacantes: String,
    val descripcion: String,
    val rubro: String,
    val sueldo: String,
    val estado: String,
    val creador: String,
    var postulantes:Array<String>,
    var contratados:Array<String>
)