package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        setContent {
            Calculadora()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Calculadora() {

    val nomesBotoes = listOf(
        // 0    1    2    3
          "1", "2", "3", "+",
        // 4    5    6    7
          "4", "5", "6", "-",
        // 8    9   10   11
          "7", "8", "9", "*",
        // 12  13   14   15
          ".", "0", "=", "/"
        )

    Column() {
        Row {
            OutlinedTextField(value = "", onValueChange = {},
                placeholder = { Text("Numeros", fontSize = 12.sp) })
                Button(onClick = {}) {
                    Text("CE", fontSize = 28.sp)

                }
            }
        for (linha in 0 .. 3) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (coluna in 0..3) {
                    Button(onClick = {}) {
                        val indice = linha * 4 + coluna
                        val texto = nomesBotoes[indice]
                        Text(texto, fontSize = 28.sp)
                    }

                }

            }
        }

    }
}


