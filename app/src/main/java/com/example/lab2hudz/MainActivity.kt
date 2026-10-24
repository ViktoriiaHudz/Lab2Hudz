
package com.example.lab2hudz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lab2hudz.ui.NoteScreen
import com.example.lab2hudz.ui.theme.Lab2HudzTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lab2HudzTheme {
                NoteScreen()
            }
        }
    }
}
