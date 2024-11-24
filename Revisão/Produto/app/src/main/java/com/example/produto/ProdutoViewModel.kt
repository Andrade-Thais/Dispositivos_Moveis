package com.example.produto

import android.util.Log
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

class ProdutoViewModel : ViewModel(){

    //declara a lista do array
    var produtos : MutableList<Produto> = mutableStateListOf()

    var nome : MutableState<String> = mutableStateOf("")
    var qtt : MutableState<String> = mutableStateOf("")
    var preco : MutableState<String> = mutableStateOf("")

    var httpClient =  OkHttpClient();
    var gson = Gson()
    var URL_BASE = "URL DO FIREBASE"


    //função dos botões

    fun gravar( onSucesso : () -> Unit, onErro : () -> Unit) {

        val p = Produto(
            nome = nome.value,
            qtt = qtt.value
        )
        try {
            p.preco = preco.value.toFloat()
        } catch (e: NumberFormatException) {
            Log.i("Produto", "Erro ao converter")
        }
        val produtoJson = gson.toJson(p)
        val request = Request.Builder()
            .post(produtoJson.toRequestBody("application.json".toMediaType()))
            .url("$URL_BASE/produto.json")
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
        httpClient.newCall(request).enqueue(response)
    }

    fun lerTodos(onSucesso: () -> Unit, onErro: () -> Unit){
        val request = Request.Builder()
            .url("$URL_BASE/produto.json")
            .get()
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onErro()
            }

            override fun onResponse(call: Call, response: Response) {
                val corpo = response.body?.string()?:"{}"
                if(corpo != "null" && corpo != "{}"){
                    val typeToken = object : TypeToken<HashMap<String?, Produto?>>(){}.type
                    val mapTemp: HashMap<String?, Produto?> =
                        gson.fromJson(corpo, typeToken)

                    produtos.clear()
                    for( entry in mapTemp){
                        val p = entry.value
                        if(p != null){
                            produtos.add( p )
                        }
                    }
                onSucesso()
                }
            }

        }
httpClient.newCall(request).enqueue(response)


    }
}