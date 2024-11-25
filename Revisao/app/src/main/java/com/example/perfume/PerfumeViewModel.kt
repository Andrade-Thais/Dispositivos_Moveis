package com.example.perfume

import androidx.compose.runtime.MutableState
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

class PerfumeViewModel : ViewModel() {

    val perfumes : MutableList<Perfume> = mutableStateListOf()
    val nome : MutableState<String> = mutableStateOf("")
    val qtd : MutableState<String> = mutableStateOf("")
    val marca : MutableState<String> = mutableStateOf("")

    val httpCliente = OkHttpClient()
    val gson = Gson()
    val URL_BASE = "url do firebase"

    fun gravar (onSucesso : () -> Unit, onErro : () -> Unit){

        val p = Perfume(
            nome = nome.value,
            qtd = qtd.value,
            marca = marca.value
        )

        val perfumeJson = gson.toJson( p )
        val request = Request.Builder()
            .post(perfumeJson.toRequestBody("application/json".toMediaType()))
            .url("$URL_BASE/perfume.json")
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onErro()
            }

            override fun onResponse(call: Call, response: Response) {
                onSucesso()
                lerTodos( {}, {})
            }

        }
        httpCliente.newCall(request).enqueue(response)
    }

    fun lerTodos (onSucesso : () -> Unit, onErro : () -> Unit){
        val request = Request.Builder()
            .url("$URL_BASE/perfume.json")
            .get()
            .build()

        val response = object : Callback{
            override fun onFailure(call: Call, e: IOException){
                onErro()
            }
            override fun onResponse (call : Call, response : Response){
                val corpo = response.body?.string() ?: "{}"
                if(corpo != "null" && corpo != "{}"){
                    val typeToken = object : TypeToken<HashMap<String?, Perfume?>>(){}.type
                    val mapTemp : HashMap<String?, Perfume?> = gson.fromJson(corpo, typeToken)

                    perfumes.clear()
                    for(entry in mapTemp){
                        val perfume = entry.value
                        if(perfume != null){
                            perfumes.add(perfume)
                        }
                    }
                    onSucesso()
                }
            }
        }
        httpCliente.newCall(request).enqueue(response)
    }
}