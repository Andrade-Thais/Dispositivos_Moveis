package com.example.pet_cadastro.activity

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pet_cadastro.model.Pet
import com.example.pet_cadastro.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class CadPetActivity : Activity() {

    private val firebaseUrl = "https://mobile-2024-2eac4-default-rtdb.firebaseio.com/pets.json"

    private lateinit var edtNome: EditText
    private lateinit var edtRaca: EditText
    private lateinit var edtPeso: EditText
    private lateinit var edtNascimento: EditText
    private lateinit var btnGravar: Button
    private lateinit var btnPesquisar: Button

    private val listaDePets = mutableListOf<Pet>()

    private val client = OkHttpClient()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_pet)

        edtNome = findViewById(R.id.edtNome)
        edtRaca = findViewById(R.id.edtRaca)
        edtPeso = findViewById(R.id.edtPeso)
        edtNascimento = findViewById(R.id.edtNasc)
        btnGravar = findViewById(R.id.btnGravar)
        btnPesquisar = findViewById(R.id.btnPesquisar)

        btnGravar.setOnClickListener {
            val pet = paraEntidade()
            pet?.let { salvarFirebase(it) }
        }

        btnPesquisar.setOnClickListener {
            pesquisar()
        }
        carregarFirebase()
    }

    private fun paraEntidade(): Pet? {
        val nome = edtNome.text.toString()
        val raca = edtRaca.text.toString()
        val peso = edtPeso.text.toString().toFloatOrNull()
        val nascimentoStr = edtNascimento.text.toString()

        if (nome.isBlank() || raca.isBlank() || peso == null || nascimentoStr.isBlank()) {
            Toast.makeText(this, "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
            return null
        }

        val nascimento: LocalDate? = try {
            val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            LocalDate.parse(nascimentoStr, inputFormatter)
        } catch (e: DateTimeParseException) {
            Toast.makeText(this, "Data de nascimento inválida.", Toast.LENGTH_SHORT).show()
            null
        }

        if (nascimento == null) {
            return null
        }

        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = nascimento.format(outputFormatter)

        return Pet(nome, raca, peso, formattedDate)
    }

    private fun paraTela(p: Pet) {
        edtNome.setText(p.nome)
        edtRaca.setText(p.raca)
        edtPeso.setText(p.peso.toString())
        edtNascimento.setText(p.nascimento)
    }

    private fun carregarFirebase() {
        val request = Request.Builder()
            .url(firebaseUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@CadPetActivity, "Erro ao carregar os pets.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $it")

                    val json = it.body?.string()

                    if (json.isNullOrEmpty()) {
                        runOnUiThread {
                            Toast.makeText(this@CadPetActivity, "Nenhum dado encontrado.", Toast.LENGTH_SHORT).show()
                        }
                        return
                    }

                    val petMapType = object : TypeToken<Map<String, Pet>>() {}.type
                    val petsMap: Map<String, Pet>? = gson.fromJson(json, petMapType)

                    if (petsMap == null) {
                        runOnUiThread {
                            Toast.makeText(this@CadPetActivity, "Erro ao carregar os dados.", Toast.LENGTH_SHORT).show()
                        }
                        return
                    }
                    listaDePets.clear()
                    listaDePets.addAll(petsMap.values)

                    runOnUiThread {
                        Toast.makeText(this@CadPetActivity, "Pets carregados com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    private fun salvarFirebase(p: Pet) {
        val json = gson.toJson(p)
        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url(firebaseUrl)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@CadPetActivity, "Erro ao salvar o pet.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $it")

                    runOnUiThread {
                        Toast.makeText(this@CadPetActivity, "Pet salvo com sucesso!", Toast.LENGTH_SHORT).show()
                        carregarFirebase()
                    }
                }
            }
        })
    }

    private fun pesquisar() {
        val nomeBusca = edtNome.text.toString()
        val petEncontrado = listaDePets.find { it.nome.contains(nomeBusca, ignoreCase = true) }
        petEncontrado?.let { paraTela(it) } ?: run {
            Toast.makeText(this, "Pet não encontrado.", Toast.LENGTH_SHORT).show()
        }
    }
}
