package ui.parts_of_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun leftPiece(){
    Column (
        modifier = Modifier //.padding(10.dp)
            .width(200.dp)
            .height(1000.dp)
            .background(Color.Red)

    ) {
        Text("Agregatka",
            modifier = Modifier.padding(4.dp), fontFamily = FontFamily.Monospace)
//                Seekbar(
//                    position = position,
//                    duration = duration.collectAsState(),
//                    onNewProgress = { },
//                    onDragStart = { },
//                    onDragEnd = {
//                        position = it.toInt()
//                    }
//                )
//                AirBar(
//                    modifier = Modifier
//                        .height(200.dp)
//                        .width(80.dp),
//                    fillColor = androidx.compose.ui.graphics.Color.Blue,
//                    backgroundColor = androidx.compose.ui.graphics.Color.LightGray,
//                    cornerRadius = 20.dp,
//                    maxValue = 100.0,
//                    minValue = 0.0,
//                    onPercentageChanged = { percentage ->
//                        // percentage changed
//                    }
//                )
        Button(

            onClick = { print("Hello!") },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray,contentColor = Color.Black),
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Button 1")
        }
        Button(

            onClick = { /* Do something! */ },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray, contentColor = Color.Black),
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Button 2")
        }
        Button(

            onClick = { /* Do something! */ },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray,contentColor = Color.Black),
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Button 3")
        }

    }
}