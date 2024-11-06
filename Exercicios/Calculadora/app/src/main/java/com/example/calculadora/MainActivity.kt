package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    // Estados da calculadora
    val displayValue = remember { mutableStateOf("") }
    val firstOperand = remember { mutableStateOf<Double?>(null) }  // Primeiro número
    val operation = remember { mutableStateOf<String?>(null) }     // Operação atual (+, -, *, /)
    val secondOperand = remember { mutableStateOf<Double?>(null) } // Segundo número


    val nomesBotoes = listOf(
        "1", "2", "3", "+",
        "4", "5", "6", "-",
        "7", "8", "9", "*",
        ".", "0", "=", "/"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campo para exibir os números
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = displayValue.value,
                onValueChange = {},
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Digite o número...", fontSize = 12.sp) }
            )
            Button(onClick = {
                displayValue.value = ""  // Limpa o valor quando "CE" é pressionado
                firstOperand.value = null
                secondOperand.value = null
                operation.value = null
            }) {
                Text("CE", fontSize = 28.sp)
            }
        }

        // Grid de botões
        for (linha in 0..3) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (coluna in 0..3) {
                    val indice = linha * 4 + coluna
                    val texto = nomesBotoes[indice]

                    // Definir cor diferente para botões de operações
                    val isOperator = texto in listOf("+", "-", "*", "/")
                    val buttonColors = if (isOperator) {
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF741308),  // Cor de fundo para operadores
                            contentColor = Color.White
                        )
                    } else {
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF000000),  // Cor de fundo para números e outros
                            contentColor = Color.White
                        )
                    }

                    Button(
                        onClick = {
                            when (texto) {
                                // Se for um número ou "."
                                in "0".."9", "." -> {
                                    displayValue.value += texto
                                }
                                // Se for uma operação (+, -, *, /)
                                in listOf("+", "-", "*", "/") -> {
                                    if (displayValue.value.isNotEmpty()) {
                                        firstOperand.value = displayValue.value.toDoubleOrNull()
                                        displayValue.value = ""
                                        operation.value = texto
                                    }
                                }
                                // Se for o "="
                                "=" -> {
                                    if (firstOperand.value != null && displayValue.value.isNotEmpty()) {
                                        secondOperand.value = displayValue.value.toDoubleOrNull()
                                        val result = calculateResult(
                                            firstOperand.value!!,
                                            secondOperand.value!!,
                                            operation.value!!
                                        )
                                        displayValue.value = result.toString()
                                        firstOperand.value = null
                                        secondOperand.value = null
                                        operation.value = null
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(4.dp),
                        colors = buttonColors
                    ) {
                        Text(texto, fontSize = 28.sp)
                    }
                }
            }
        }
    }
}

// Função para calcular o resultado
fun calculateResult(firstOperand: Double, secondOperand: Double, operation: String): Double {
    return when (operation) {
        "+" -> firstOperand + secondOperand
        "-" -> firstOperand - secondOperand
        "*" -> firstOperand * secondOperand
        "/" -> firstOperand / secondOperand
        else -> 0.0
    }
}
