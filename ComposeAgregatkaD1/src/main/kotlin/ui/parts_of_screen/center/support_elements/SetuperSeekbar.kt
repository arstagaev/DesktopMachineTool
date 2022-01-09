package ui.parts_of_screen.center.support_elements

import Seekbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun seekbarSetup(
    pressure1: Int,
    pressure2: Int,
    pressure3: Int,
    pressure4: Int,
    pressure5: Int,
    pressure6: Int,
    pressure7: Int,
    pressure8: Int,
    sizeRow: Size,
    duration: MutableStateFlow<Long>
) {
    var position1SeekBar by remember { mutableStateOf(10) }
    var position2SeekBar by remember { mutableStateOf(10) }
    var position3SeekBar by remember { mutableStateOf(10) }
    var position4SeekBar by remember { mutableStateOf(10) }

    var position5SeekBar by remember { mutableStateOf(10) }
    var position6SeekBar by remember { mutableStateOf(10) }
    var position7SeekBar by remember { mutableStateOf(10) }
    var position8SeekBar by remember { mutableStateOf(10) }
    Row(
        modifier = androidx.compose.ui.Modifier //.padding(10.dp)
            .width(sizeRow.width.dp)
            .height(IntrinsicSize.Min)
            .background(Color.White)
    ) {
        justBar("Канал 1",pressure1,duration)
        justBar("Канал 2",pressure2,duration)
        justBar("Канал 3",pressure2,duration)
        justBar("Канал 4",pressure2,duration)

    }
    Row(
        modifier = androidx.compose.ui.Modifier //.padding(10.dp)
            .width(sizeRow.width.dp)
            .height(IntrinsicSize.Min)
            .background(Color.White)
    ) {
        justBar("Канал 5",pressure1,duration)
        justBar("Канал 6",pressure2,duration)
        justBar("Канал 7",pressure2,duration)
        justBar("Канал 8",pressure2,duration)

    }



}


@Composable
fun justBar(channelName : String,pressure1: Int, duration: MutableStateFlow<Long>,) {
    Column(
        modifier = Modifier.padding(0.dp, 1.dp)
            .width(200.dp)
            .height(90.dp)
            .background(Color.White)
            .padding(5.dp)
        //.fillMaxWidth()
    ) {
        var position1SeekBar by remember { mutableStateOf(10) }

        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.DarkGray),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("${channelName}     ", modifier = Modifier.padding(2.dp), fontSize = 12.sp)
                Text(" Current: ${pressure1} \n PWN: 0", fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.width(3.dp))
            Button(
                onClick = { /* Do something! */ },
                modifier = Modifier.size(width = 40.dp, height = 40.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Green,

                    )
            ) {
                Text("+")
            }
            //Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = { /* Do something! */ },
                modifier = Modifier.size(width = 40.dp, height = 40.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Red,

                    )
            ) {
                Text("-")
            }

        }
        Row(
            modifier = Modifier.height(20.dp)
        ) {
            Seekbar(
                position = position1SeekBar,
                duration = duration.collectAsState(),
                onNewProgress = { },
                onDragStart = { },
                onDragEnd = {
                    position1SeekBar = it.toInt()
                }
            )
        }
    }
}