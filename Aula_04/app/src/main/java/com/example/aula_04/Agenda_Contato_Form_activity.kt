package com.example.aula_04

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

data class Contato(
    var nome: String ="",
    var email: String ="",
    var telefone: String =""
)

class Agenda_Contato_Form_activity: Activity() {

    val lista = ArrayList<Contato>()

    override fun onCreate (bundle: Bundle?){
        super.onCreate(bundle)
        setContentView(R.layout.agenda_contato_form_layout)

        val edtNome : EditText = findViewById(R.id.edt_nome)
        val edtEmail = findViewById<EditText>(R.id.edt_email)
        val edtTelefone = findViewById<EditText>(R.id.edt_telefone)

        val btnGravar = findViewById<Button>(R.id.btn_gravar)
        val btnPesquisar = findViewById<Button>(R.id.btn_pesquisar)

        btnGravar.setOnClickListener{
            val contato = Contato(
                nome = edtNome.text.toString(),
                email = edtEmail.text.toString(),
                telefone = edtTelefone.text.toString()
            )
            lista.add(contato)

            Toast.makeText(this,
                "Contato gravado com sucesso", Toast.LENGTH_LONG).show()
            edtNome.setText("")
            edtTelefone.setText("")
            edtEmail.setText("")

        }

        btnPesquisar.setOnClickListener {
            for (contato in lista) {
                if (contato.nome.contains(edtNome.text)) {
                    edtNome.setText(contato.nome)
                    edtEmail.setText(contato.email)
                    edtTelefone.setText(contato.telefone)
                    break
                }
            }
        }
    }

}