package com.example.aula_05

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class AgendaContatoAPIActivity : Activity() {
    val URL_BASE = "https://mobile-2024-2eac4-default-rtdb.firebaseio.com"
    override fun onCreate (bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.agenda_contato_form_layout)

        val edtNome = findViewById<EditText>(R.id.edt_nome)
        val edtEmail = findViewById<EditText>(R.id.edt_email)
        val edtTelefone = findViewById<EditText>(R.id.edt_telefone)
        val btnGravar = findViewById<Button>(R.id.btn_gravar)
        val client = OkHttpClient()
        btnGravar.setOnClickListener{
            val contatoJson =
            """
                {
                    "nome": "${edtNome.text}",
                    "email": "${edtEmail.text}",
                    "telefone": "${edtTelefone.text}
                }
            """.trimIndent()

            val request = Request.Builder()
                .url("$URL_BASE/contato.json")
                .post(contatoJson.toRequestBody(
                    "application/json".toMediaType()
                ))
                .build()

            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.v("Agenda", "Erro: ${e.message} ao cadastrar contato")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.v("Agenda", "Contato cadastrado com sucesso!")
                }
            }

            client.newCall( request ).enqueue( response )
        }
   }
}