package edu.mobileI.aula_02_calculadora_soma

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class CalculadoraSomaActivity : Activity(){
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        Log.v("CALCULADORA", "Activity criada pelo onCreate()")
        setContentView(R.layout.calculadora_layout)

        var edtNum1 = findViewById<EditText>(R.id.edtNumero1)
        var edtNum2 = findViewById<EditText>(R.id.edtNumero2)
        var btnSomar = findViewById<Button>(R.id.btnSomar)
        var btnSubtrair = findViewById<Button>(R.id.btnSub)

        btnSomar.setOnClickListener {
            var num1 = edtNum1.text.toString().toDoubleOrNull()
            var num2 = edtNum2.text.toString().toDoubleOrNull()
            if (num1 == null) {
                num1 = 0.0
            }
            if (num2 == null) {
                num2 = 0.0
            }
            Log.v("CALCULADORA", "Resultado: ${num1 + num2}")
        }
    }
    override fun onStart() {
        super.onStart()
        Log.v("CALCULADORA", "Activity inicializada com sucesso via onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.v("CALCULADORA", "Activity visível via onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.v("CALCULADORA", "Activity pausada (invisível) via onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.v("CALCULADORA", "Activity parada via onStop()")
    }

    override fun onDestroy() {
        Log.v("CALCULADORA", "Activity destruída via onDestroy()")
        super.onDestroy()

    }
}