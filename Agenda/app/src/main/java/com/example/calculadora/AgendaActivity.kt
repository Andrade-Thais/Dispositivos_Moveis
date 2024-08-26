package com.example.calculadora

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class AgendaActivity: Activity(){

 override fun onCreate(bundle: Bundle?){
     super.onCreate(bundle)
     var edtNome = findViewById<EditText>(R.id.edtNome)
     var edtEmail = findViewById<EditText>(R.id.edtEmail)
     var edtTelefone = findViewById<EditText>(R.id.edtTelefone)

     var btnSalvar = findViewById<Button>(R.id.btnSalvar)

     btnSalvar.setOnClickListener{
         var nome = edtNome.text.toString()
         var email = edtEmail.text.toString()
         var telefone = edtTelefone.text.toString()
         Log.v("Agenda", "Nome:  ${nome} Email: ${email}, Telefone: ${telefone}")
     }
 }

}
