package com.example.aula_07.reciycleView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aula_07.R
import com.example.aula_07.model.Contato

class ContatoAdapter(val contexto : Context, val lista : List<Contato>): RecyclerView.Adapter<ContatoViewHolder>() {
    lateinit var inflater : LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        inflater = LayoutInflater.from(contexto);
        val view = inflater.inflate(R.layout.contato_item_layout,
            parent, false)

        return ContatoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val contato = lista[position]
        holder.txtNome.text = contato.nome
        holder.txtEmail.text = contato.email
        holder.txtTelefone.text = contato.telefone
    }
}