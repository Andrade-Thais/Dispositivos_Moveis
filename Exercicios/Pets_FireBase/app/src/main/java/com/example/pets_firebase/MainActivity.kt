package com.example.pets_firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pets_firebase.ui.theme.Pets_FireBaseTheme

class MainActivity : ComponentActivity() {
    val viewModel : PetViewModel by viewModels<PetViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pets_FireBaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Formulario(
                        titulo = "Pet Space",
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Formulario(
    titulo: String,
    viewModel: PetViewModel,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current


    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = titulo,
            modifier = modifier
        )
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Nome: ")
            OutlinedTextField(
                value = viewModel.nome.value,
                onValueChange = { viewModel.nome.value = it } )
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Raça: ")
            OutlinedTextField(
                value = viewModel.raca.value,
                onValueChange = { viewModel.raca.value = it })

        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Peso: ")
            OutlinedTextField(
                value = viewModel.peso.value.toString(),
                onValueChange = { pesoString ->  viewModel.peso.value  =
                    pesoString.toFloatOrNull()!!
                })

        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Idade: ")
            OutlinedTextField(
                value = viewModel.idade.value.toString(),
                onValueChange = { idadeString ->  viewModel.idade.value  =
                    idadeString.toUIntOrNull()!!
                })

        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = {
                viewModel.gravar()
                Toast.makeText(
                    context,
                    "Pet Salvo com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Text(text = "Gravar")
            }
            Button(onClick = { viewModel.lerTodos() }) {
                Text(text = "Pesquisar")
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(), // Ocupa o tamanho total da tela
            contentAlignment = Alignment.Center // Centraliza o conteúdo
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally // Centraliza horizontalmente cada item
            ) {
                item {
                    Text(text = "Pets")
                }
                items(items = viewModel.petLista) { pet ->
                    Card(modifier = Modifier.padding(8.dp)) { // Adiciona um padding para espaçamento entre os cards
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally // Centraliza o conteúdo dentro do Card
                        ) {
                            Text(text = pet.nome)
                            Text(text = pet.raca)
                            Text(text = pet.peso.toString())
                            Text(text = pet.idade.toString())
                        }
                    }
                }
            }
        }
    }}