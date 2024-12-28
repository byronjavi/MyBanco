package com.ejercicios.mybanco

import android.R

data class Usuario(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val cedula: String,
    val direccion: String,
    val usuario: String,
    val pass: String,
    val saldo: Float
)
