package com.example.aula_06


//data class sobrescreve o hashcode, equals e toString
data class Contato (
    var nome: String = "",
    var email: String = "",
    var telefone: String = ""
){

}

//fun main() = {
//    val c1 = Contato(email="thais@exemplo.com", telefone="(11)2233-4455",nome="thais andrade")
//    println(c1)
//}