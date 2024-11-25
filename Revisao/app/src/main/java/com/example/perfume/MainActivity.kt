package com.example.perfume

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.perfume.ui.theme.PerfumeTheme

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<PerfumeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.lerTodos({}, {})
        enableEdgeToEdge()
        setContent {
            PerfumeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PerfumeScreen(innerPadding)

                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfumeScreen(innerPadding: PaddingValues) {
    val image: Painter =
        painterResource(id = R.drawable.problema) // Substitua pelo seu recurso de imagem

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1F38)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = image,
            contentDescription = "Perfume Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = "Perfume Manager",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(Color(0xFF3A3F58), shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.nome.value,
                onValueChange = { viewModel.nome.value = it },
                label = { Text("Nome do Perfume") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )
            TextField(
                value = viewModel.qtd.value,
                onValueChange = { viewModel.qtd.value = it },
                label = { Text("Quantidade") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )
            TextField(
                value = viewModel.marca.value,
                onValueChange = { viewModel.marca.value = it },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    viewModel.gravar(
                        onSucesso = {
                            runOnUiThread {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Perfume salvo com sucesso!", Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        onErro = {
                            runOnUiThread {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Erro ao salvar o perfume.", Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B76F7))
            ) {
                Text("Salvar")
            }

            Button(
                onClick = { viewModel.lerTodos({}, {}) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B76F7))
            ) {
                Text("Carregar")
            }
        }

        // Lista de perfumes
        LazyColumn{
        items(viewModel.perfumes){ perfume ->
            Text(perfume.nome, color = Color.White)
            }
        }
    }
}
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PerfumeTheme {
            PerfumeScreen(PaddingValues(10.dp))
        }
    }
}

