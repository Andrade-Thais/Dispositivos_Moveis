@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pets_lista

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pets_lista.ui.theme.Pets_ListaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pets_ListaTheme {
                Formulario()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Formulario() {
    var nome by remember { mutableStateOf("") }
    var raca by remember { mutableStateOf("") }
    var peso by remember { mutableFloatStateOf(0.0f) }
    var idade by remember { mutableStateOf(0u) }
    var petsLista = remember { mutableListOf<Pets>() }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.pet), // imagem de fundo
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Espaço Pet",
                fontSize = 50.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.8f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InputField(label = "Nome", value = nome, onValueChange = { nome = it })
                    InputField(label = "Raça", value = raca, onValueChange = { raca = it })
                    InputField(label = "Peso", value = peso.toString(), onValueChange = { peso = it.toFloat() })
                    InputField(label = "Idade", value = idade.toString(), onValueChange = { idade = it.toUInt() })

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            if (nome.isNotEmpty() && raca.isNotEmpty() && peso > 0f && idade > 0u) {
                                val pet = Pets(
                                    nome = nome,
                                    raca = raca,
                                    peso = peso,
                                    idade = idade
                                )
                                petsLista.add(pet)
                                Toast.makeText(
                                    context,
                                    "Pet Gravado com sucesso!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                nome = ""
                                raca = ""
                                peso = 0.0f
                                idade = 0u
                            }
                        }) {
                            Text(text = "Gravar")
                        }

                        Button(onClick = {
                            var found = false
                            petsLista.forEach { pets ->
                                if (pets.nome == nome) {
                                    raca = pets.raca
                                    peso = pets.peso
                                    idade = pets.idade
                                    found = true
                                }
                            }
                            if (!found) {
                                Toast.makeText(
                                    context,
                                    "Nenhum Pet Encontrado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Text(text = "Pesquisar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(

            // Definindo a cor do texto
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,


            // Definindo a cor do label quando focado e não focado
            focusedLabelColor = Color.Red,
            unfocusedLabelColor = Color.Blue
        )
    )
}

