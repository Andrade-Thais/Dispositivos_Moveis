package com.example.pets_firebase

data class Pet(
    var nome : String = "",
    var raca : String = "",
    var peso : Float = 0.0f,
    var idade : UInt = 0u
)