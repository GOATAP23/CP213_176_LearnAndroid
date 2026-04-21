package org.ananchanon.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.ananchanon.project.ui.PokedexScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        PokedexScreen()
    }
}