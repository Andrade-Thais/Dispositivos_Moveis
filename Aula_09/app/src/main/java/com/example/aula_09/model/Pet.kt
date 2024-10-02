package com.example.aula_09.model

import android.icu.text.DateFormat
import java.time.LocalDate

//diferença de val e var é que val não pode ser alterado
// data class classe que guardam dados

data class Pet (
    var nome : String = "",
    var raca : String = "",
    var peso : Float = 0.0f, //colocar f para sinalizar que é float
    var nasc : LocalDate = LocalDate.now() //localDate.now = data de hoje.
)