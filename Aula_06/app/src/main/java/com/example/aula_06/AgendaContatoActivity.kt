package com.example.aula_06

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

import java.io.IOException

class AgendaContatoActivity : Activity() {

    val URL_BASE = "https://mobile-2024-2eac4-default-rtdb.firebaseio.com"

    //responsável por iniciar a aplicação
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.agenda_contato_form_layout)

        //Coletar os dados do formulário
        val nome = findViewById<EditText>(R.id.edt_nome)
        val email = findViewById<EditText>(R.id.edt_email)
        val telefone = findViewById<EditText>(R.id.edt_telefone)

        //botões de interação
        val btnGravar = findViewById<Button>(R.id.btn_gravar)
        val btnPesquisa = findViewById<Button>(R.id.btn_pesquisar)

        val gson = Gson()
        val cliente = OkHttpClient()

        btnGravar.setOnClickListener {
            val contato = Contato(
                nome = nome.text.toString(),
                email = email.text.toString(),
                telefone = telefone.text.toString()
            )
            val contatoJson = gson.toJson(contato)
            Log.d("Agenda", "JSON criado: $contatoJson")

            val request = Request.Builder()
                .url("$URL_BASE/contato.json")
                .post(
                    contatoJson.toRequestBody(
                        "application/json".toMediaType()
                    )
                )
                .build()

            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(
                            this@AgendaContatoActivity, "Erro ${e.message} ao gravar contato",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        Toast.makeText(
                            this@AgendaContatoActivity, "Dados cadastrados com sucesso",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            cliente.newCall(request).enqueue(response)

        }

        btnPesquisa.setOnClickListener {
            val resquest = Request.Builder()
                .url("$URL_BASE/contatos.json")
                .get()
                .build()


            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(
                            this@AgendaContatoActivity,
                            "Erro: ${e.message} ao cadastrar o contato\"",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val contatosJson = response.body?.string()
                    Log.d("AGENDA", "Rspostas : $contatosJson")
                    val contatos: HashMap<String?, Contato?> =
                        gson.fromJson(contatosJson, HashMap<String?, Contato?>()::class.java)

                    for (contato in contatos.values) {
                        Log.d("AGENDA", "Contato: $contato")

                    }
                }
            }

            cliente.newCall(resquest).enqueue(response)
        }

    }

    }



