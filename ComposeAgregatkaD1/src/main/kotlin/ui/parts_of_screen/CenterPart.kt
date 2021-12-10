package ui.parts_of_screen

import Gauge
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun centerPiece(
    pressure1 : Int,
    pressure2 : Int,
    pressure3 : Int,
    pressure4 : Int,
    pressure5 : Int,
    pressure6 : Int,
    pressure7 : Int,
    pressure8 : Int,
    //sizeRowRAW : IntSize
) {
    var sizeRow by remember { mutableStateOf(Size.Zero) }

    var position1SeekBar by remember { mutableStateOf(10) }
    var position2SeekBar by remember { mutableStateOf(10) }
    var position3SeekBar by remember { mutableStateOf(10) }
    var position4SeekBar by remember { mutableStateOf(10) }

    var position5SeekBar by remember { mutableStateOf(10) }
    var position6SeekBar by remember { mutableStateOf(10) }
    var position7SeekBar by remember { mutableStateOf(10) }
    var position8SeekBar by remember { mutableStateOf(10) }

    val duration = MutableStateFlow(100L)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {

        Column(

            modifier = Modifier //.padding(10.dp)
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
                .background(Color.White)
                .layoutId("r")
                .onGloballyPositioned { coordinates ->
                    sizeRow = coordinates.size.toSize()
                }
        ) {
            Row {

                Box {
                    Gauge(pressure = pressure1, name = "#1")
                }
                Box {
                    Gauge(pressure = pressure2 + 12, name = "#2")
                }
                Box {
                    Gauge(pressure = pressure3, name = "#3")
                }
                Box {
                    Gauge(pressure = pressure4 + 12, name = "#4")
                }
            }
            Row {
                Box {
                    Gauge(pressure = pressure5 - 34, name = "#5")
                }
                Box {
                    Gauge(pressure = pressure6 - 3, name = "#6")
                }
                Box {
                    Gauge(pressure = pressure7, name = "#7")
                }
                Box {
                    Gauge(pressure = pressure8 + 12, name = "#8")
                }
            }
            Row(
                modifier = androidx.compose.ui.Modifier //.padding(10.dp)
                    .width(sizeRow.width.dp)
                    .height(IntrinsicSize.Max)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(0.dp, 10.dp)
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.White)
                        .padding(5.dp)
                    //.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.DarkGray),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Channel 1     ", modifier = Modifier.padding(2.dp), fontSize = 12.sp)
                            Text(" Current: ${pressure1} \n PWN: 1209", fontSize = 12.sp)
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
                        modifier = Modifier
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

                Column(
                    modifier = Modifier.padding(0.dp, 10.dp)
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Magenta)
                        .padding(5.dp)
                    //.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.DarkGray),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Channel 1     ", modifier = Modifier.padding(2.dp), fontSize = 12.sp)
                            Text(" Current: ${pressure1} \n PWN: 1209", fontSize = 12.sp)
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
                        modifier = Modifier
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

                Column(
                    modifier = Modifier.padding(0.dp, 10.dp)
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.White)
                        .padding(5.dp)
                    //.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.DarkGray),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Channel 1     ", modifier = Modifier.padding(2.dp), fontSize = 12.sp)
                            Text(" Current: ${pressure1} \n PWN: 1209", fontSize = 12.sp)
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
                        modifier = Modifier
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

                Column(
                    modifier = Modifier.padding(0.dp, 10.dp)
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.White)
                        .padding(5.dp)
                    //.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.DarkGray),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Channel 1     ", modifier = Modifier.padding(2.dp), fontSize = 12.sp)
                            Text(" Current: ${pressure1} \n PWN: 1209", fontSize = 12.sp)
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
                        modifier = Modifier
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
            ///////////

            Row(
                modifier = androidx.compose.ui.Modifier //.padding(10.dp)
                    .width(sizeRow.width.dp)
                    .height(IntrinsicSize.Max)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(0.dp, 10.dp)
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.White)
                        .padding(5.dp)
                    //.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.DarkGray),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Channel 1     ", modifier = Modifier.padding(2.dp), fontSize = 12.sp)
                            Text(" Current: ${pressure1} \n PWN: 1209", fontSize = 12.sp)
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
                        modifier = Modifier
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

                Column(
                    modifier = Modifier.padding(0.dp, 10.dp)
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Magenta)
                        .padding(5.dp)
                    //.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.DarkGray),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Channel 1     ", modifier = Modifier.padding(2.dp), fontSize = 12.sp)
                            Text(" Current: ${pressure1} \n PWN: 1209", fontSize = 12.sp)
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
                        modifier = Modifier
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

                Column(
                    modifier = Modifier.padding(0.dp, 10.dp)
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.White)
                        .padding(5.dp)
                    //.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.DarkGray),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Channel 1     ", modifier = Modifier.padding(2.dp), fontSize = 12.sp)
                            Text(" Current: ${pressure1} \n PWN: 1209", fontSize = 12.sp)
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
                        modifier = Modifier
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

                Column(
                    modifier = Modifier.padding(0.dp, 10.dp)
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.White)
                        .padding(5.dp)
                    //.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.DarkGray),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Channel 1     ", modifier = Modifier.padding(2.dp), fontSize = 12.sp)
                            Text(" Current: ${pressure1} \n PWN: 1209", fontSize = 12.sp)
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
                        modifier = Modifier
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
        }
    }
}

fun channelEditor(pressure1: Int, position1SeekBar: Int, duration: MutableStateFlow<Long>) {

}

fun onesAndTens(onesRAW : Byte,tensRAW : Byte) : Double{
    var ones = onesRAW.toUInt()
    var tens = tensRAW.toUInt()

    if (tens == 0u) {

        return ones.toDouble()

    } else {

        return ( ones + tens * 255u ).toDouble()

    }
}
