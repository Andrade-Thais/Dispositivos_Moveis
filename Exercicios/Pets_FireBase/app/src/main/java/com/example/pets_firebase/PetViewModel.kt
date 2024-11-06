package com.example.pets_firebase

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class PetViewModel : ViewModel(){
    val petLista = mutableStateListOf<Pet>()
    val nome = mutableStateOf("")
    val raca = mutableStateOf("")
    val peso = mutableFloatStateOf(0.0f)
    val idade = mutableStateOf(0u)

    val URL_BASE = "https://mobile-2024-2eac4-default-rtdb.firebaseio.com"
    val gson = Gson()
    val httpClient = OkHttpClient()

    fun gravar(){
        val pet = Pet(
            nome = nome.value,
            raca = raca.value,
            peso = peso.value,
            idade = idade.value
        )
        val petJson = gson.toJson(pet)

        val request = Request.Builder()
            .url("$URL_BASE/pets.json")
            .post(petJson.toRequestBody(
                "application/json".toMediaType()
            )).build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("PET","ERRO AO GRAVAR O PET ${e.message}",e)

            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("PET","PET GRAVADO COM SUCESSO!")
            }
        }
        httpClient.newCall(request).enqueue(response)

    }

    fun lerTodos() {
        val request = Request.Builder()
            .url("$URL_BASE/pets.json")
            .get()
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("PET", "ERRO AO BUSCAR PETS", e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("PET", "SUCESSO AO BUSCAR OS PETS!")
                val body = response.body?.string() ?: "null"
                if (body != "null") {
                    val typeToken = object : TypeToken<HashMap<String?, Pet?>>() {}.type
                    val petsMap: HashMap<String?, Pet?> = gson.fromJson(body, typeToken)

                    petLista.clear()
                    petsMap.values.filterNotNull().forEach {
                        petLista.add(it)
                    }
                }
            }
        }
        httpClient.newCall(request).enqueue(response)
    }
}