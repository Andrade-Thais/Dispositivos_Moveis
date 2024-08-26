package edu.mobileI.aula_02_calculadora_soma

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CalculadoraSomaActivity : Activity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        Log.v("CALCULADORA", "Activity criada pelo onCreate()")
        setContentView(R.layout.calculadora_layout)

        val edtNum1 = findViewById<EditText>(R.id.edtNumero1)
        val edtNum2 = findViewById<EditText>(R.id.edtNumero2)
        val btnSomar = findViewById<Button>(R.id.btnSomar)
        val btnSubtrair = findViewById<Button>(R.id.btnSub)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        btnSomar.setOnClickListener {
            val num1 = edtNum1.text.toString().toDoubleOrNull() ?: 0.0
            val num2 = edtNum2.text.toString().toDoubleOrNull() ?: 0.0
            val resultado = num1 + num2

            Log.v("CALCULADORA", "Resultado: $resultado")
            tvResultado.text = "Resultado: $resultado"
        }

        btnSubtrair.setOnClickListener {
            val num1 = edtNum1.text.toString().toDoubleOrNull() ?: 0.0
            val num2 = edtNum2.text.toString().toDoubleOrNull() ?: 0.0
            val resultado = num1 - num2

            Log.v("CALCULADORA", "Resultado: $resultado")
            tvResultado.text = "Resultado: $resultado"
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





