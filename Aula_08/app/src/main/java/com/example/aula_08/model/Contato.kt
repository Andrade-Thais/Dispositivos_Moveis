package com.example.aula_08.model

import java.io.Serializable

data class Contato (

    var id: String = "",
    var nome: String = "",
    var email: String = "",
    var telefone: String = ""
): Serializable
