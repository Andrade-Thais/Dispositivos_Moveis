package com.example.aula_07.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aula_07.R
import com.example.aula_07.model.Contato
import com.example.aula_07.reciycleView.ContatoAdapter

class ContatoListaActivity : Activity() {

    val lista = ArrayList<Contato>()

    override fun onCreate(bundle: Bundle?){
        super.onCreate(bundle)
        setContentView(R.layout.contato_lista_activity)

        lista.add(Contato("Thais Andrade", "(11)1122-3344", "thais@exemple.com"))
        lista.add(Contato("joao Andrade", "(11)1122-3344", "thais@exemple.com"))
        lista.add(Contato("maria Andrade", "(11)1122-3344", "thais@exemple.com"))

        val adapter = ContatoAdapter(this, lista)
        val recycler = findViewById<RecyclerView>(R.id.rcv_contato_lista)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }
}