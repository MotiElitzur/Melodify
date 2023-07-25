package com.motiapps.melodify.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.motiapps.melodify.navigation.NavGraph
import com.motiapps.melodify.ui.theme.MelodifyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MelodifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph()
                }
            }
        }
    }
}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//
//    Column(
//        modifier = modifier
//            .background(Color.Green)
//            .fillMaxSize()
//            .padding(32.dp)
//            .border(2.dp, Color.Blue),
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.Center
//
//    ) {
//        Text(
//            text = "Hello $name!",
//            modifier = modifier
//                .offset(100.dp, (-50).dp)
//        )
//
//        Spacer(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//                .background(
//                    color = Color.Red
//                )
//        )
//        Text(
//            text = "Hello $name!",
//
//            )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MelodifyTheme {
//        Greeting("Moti")
//    }
//}