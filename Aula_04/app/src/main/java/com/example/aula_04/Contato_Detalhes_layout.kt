package com.example.aula_04

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class Contato_Detalhes_layout: Activity() {


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        setContentView(R.layout.contato_detalhes_layout)

        val btnVoltar = findViewById<Button>(R.id.btn_voltar)
        btnVoltar.setOnClickListener {
            val intent = Intent(this, Contato_Detalhes_layout::class.java)
            startActivity(intent)
        }
    }
}
