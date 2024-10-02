package com.example.aula_09.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.aula_09.R
import com.example.aula_09.model.Pet
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PetActivity : Activity() {

    val URL_BASE = "https://mobile-2024-2eac4-default-rtdb.firebaseio.com" // URL do realDataBase do Firebase
    val lista = ArrayList<Pet>()
    lateinit var edtNome: EditText
    lateinit var edtRaca: EditText
    lateinit var edtPeso: EditText
    lateinit var edtNasc: EditText

    val clienteHttp = OkHttpClient()
    val gson = Gson()

    var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    override fun onCreate(bundle: Bundle?) { // Carga de informação bundle (banco de dados)
        super.onCreate(bundle)

        // Comando do layout
        setContentView(R.layout.cadastro_pet)

        // Pegar as informações do layout
        edtNome = findViewById(R.id.edtNome)
        edtRaca = findViewById(R.id.edtRaca)
        edtPeso = findViewById(R.id.edtPeso)
        edtNasc = findViewById(R.id.edtNasc)

        //botão gravar
        val btnGravar = findViewById<Button>(R.id.btnGravar)
        val btnPesquisar = findViewById<Button>(R.id.btnPesquisar)
        btnGravar.setOnClickListener {
            val p = paraEntidade()
            salvarFirebase( p )
            carregarFirebase()
        }

        btnPesquisar.setOnClickListener {
            for (pet in lista){
                if (pet.nome.contains( edtNome.text )) {
                    paraTela( pet )
                }
            }
        }
    }

    fun paraEntidade(): Pet {
        val p = Pet()
        p.nome = edtNome.text.toString()
        p.raca = edtRaca.text.toString()
        p.peso = edtPeso.text.toString().toFloat() // Correção aqui
        p.nasc = LocalDate.parse(edtNasc.text.toString(), formatter)
        return p
    }

    fun paraTela(p: Pet) {
        edtNome.setText(p.nome)
        edtRaca.setText(p.raca)
        edtPeso.setText(p.peso.toString()) // Adicionando conversão para String
        val txtNascimento: String = p.nasc.format(formatter)
        edtNasc.setText(txtNascimento)
    }

    fun carregarFirebase() {
        val request = Request.Builder()
            .get()
            .url("$URL_BASE/pets.json")
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("PET", "Erro ao ler os dados", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val textJson = response.body?.string() ?: return // Protegendo contra null
                val mapa: HashMap<String, Pet> =
                    gson.fromJson(textJson, HashMap<String, Pet>()::class.java)

                lista.clear()
                for (petItem in mapa) {
                    lista.add(petItem.value)
                }
            }

        }
        clienteHttp.newCall(request).enqueue(response)
    }

        fun salvarFirebase( p : Pet){
            val petJson = gson.toJson( p )

            val request = Request.Builder()
                .post(petJson.toRequestBody(
                    "application/json".toMediaType()
                ))
                .url("$URL_BASE/pets.json")
                .build()
            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("PET", "Erro ao gravar pet!")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.e("PET", "Pet gravado com sucesso!")
                }
            }
            clienteHttp.newCall(request).enqueue(response)
        }
}
