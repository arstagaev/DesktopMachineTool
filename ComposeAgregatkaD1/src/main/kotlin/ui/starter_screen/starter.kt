package ui.starter_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screenNav
import ui.navigation.Screens

@Composable
fun StarterScreen() {
    Column(Modifier.fillMaxSize().background(Color.Black)) {
        Row(modifier = Modifier.weight(1f)) {
            LazyColumn {
                item {
                    Text("Scena 1")
                }
                item {
                    Text("Scena 2")
                }
                item {
                    Text("Scena 3")
                }
            }
        }
        Row(modifier = Modifier.weight(2f)) {
            LazyColumn {
                item {
                    Text("Start!",
                        modifier = Modifier.width(200.dp).height(60.dp).padding(4.dp).clickable {
                            screenNav.value = Screens.MAIN
                        },fontSize = 14.sp, fontFamily = FontFamily.Monospace, )
                }
            }
        }
    }
}