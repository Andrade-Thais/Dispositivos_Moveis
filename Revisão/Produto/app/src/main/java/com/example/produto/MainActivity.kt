package com.example.produtos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.example.produto.ProdutoViewModel
import com.example.produto.R
import com.example.produto.ui.theme.ProdutoTheme

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<ProdutoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProdutoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CadastroDeProdutosScreen(innerPadding, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroDeProdutosScreen(innerPadding: PaddingValues, viewModel: ProdutoViewModel) {
    val image: Painter = painterResource(id = R.drawable.solucao)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181F38)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            contentDescription = "Cadastro de Produtos",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Text(
            text = "Cadastro de Produtos",
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
            // Campo Nome
            TextField(
                value = viewModel.nome.value,
                onValueChange = { viewModel.nome.value = it },
                label = { Text("Nome do Produto") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
            )

            // Campo Quantidade
            TextField(
                value = viewModel.qtt.value,
                onValueChange = { viewModel.qtt.value = it },
                label = { Text("Quantidade") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
            )

            // Campo Preço
            TextField(
                value = viewModel.preco.value,
                onValueChange = { viewModel.preco.value = it },
                label = { Text("Preço (R$)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    viewModel.gravar(
                        onSucesso = {
                            Toast.makeText(
                                this@MainActivity,
                                "Produto cadastrado com sucesso!",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onErro = {
                            Toast.makeText(
                                this@MainActivity,
                                "Erro ao cadastrar produto",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B76F7))
            ) {
                Text("Gravar")
            }

            Button(
                onClick = { /* Lógica de pesquisa */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B76F7))
            ) {
                Text("Pesquisar")
            }
        }

        // Lista de Produtos
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(viewModel.produtos) { produto ->
                Text(
                    text = "${produto.nome} - ${produto.qtt} - R$ ${produto.preco}",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(0xFF3A3F58), shape = RoundedCornerShape(4.dp))
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCadastroDeProdutos() {
    ProdutoTheme {
        CadastroDeProdutosScreen(PaddingValues(10.dp), ProdutoViewModel())
    }
}
