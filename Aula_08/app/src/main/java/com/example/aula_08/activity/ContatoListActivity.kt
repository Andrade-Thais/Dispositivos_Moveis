package com.example.aula_08.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aula_08.R
import com.example.aula_08.model.Contato
import com.example.aula_08.recycleView.ContatoAdapter

class ContatoListActivity: Activity() {

        val lista = ArrayList<Contato>()
        override fun onCreate(bundle: Bundle?) {
            super.onCreate(bundle)
            setContentView(R.layout.contato_lista_activity)

            val b1 = intent.extras
            val lista = b1?.getSerializable("lista") as ArrayList<Contato> ?: ArrayList<Contato>()

//  lista.add(Contato("1", "Joao", "20202202020", "jaldsjddsalkjaslkj"))
//  lista.add(Contato("2", "Maria", "33243243232", "wsflksçkçfk"))
//  lista.add(Contato("3", "Eu", "330403490349", "dflsjflsjflskjs"))

            val adapter = ContatoAdapter(this, lista)
            val recycler = findViewById<RecyclerView>(R.id.rcv_contato_lista)

            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(this)
        }

    }
