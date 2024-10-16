package com.example.aula_11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aula_11.ui.theme.Aula_11Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        setContent {
            HelloWorld()
        }
    }
}
@Preview(showBackground = true)
        @Composable
        fun HelloWorld(){
            Column  (modifier=Modifier
                .padding(30.dp)
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(modifier = Modifier
                    .background(Color.Yellow)
                    .padding(30.dp),
                    text="Hello World, Thais. Como Está? ") //JetPack Compose
                Button(onClick = {}) {
                    Text("Ok")

                }
            }

}

