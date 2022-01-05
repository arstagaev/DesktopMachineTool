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
        Column(
            modifier = Modifier.padding(0.dp, 1.dp)
                .weight(1f)
                .height(120.dp)
                .background(Color.Red)
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

        Column(
            modifier = Modifier.padding(0.dp, 10.dp)
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Transparent)
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
                    position = position2SeekBar,
                    duration = duration.collectAsState(),
                    onNewProgress = { },
                    onDragStart = { },
                    onDragEnd = {
                        position2SeekBar = it.toInt()
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
                    position = position3SeekBar,
                    duration = duration.collectAsState(),
                    onNewProgress = { },
                    onDragStart = { },
                    onDragEnd = {
                        position3SeekBar = it.toInt()
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
                    position = position4SeekBar,
                    duration = duration.collectAsState(),
                    onNewProgress = { },
                    onDragStart = { },
                    onDragEnd = {
                        position4SeekBar = it.toInt()
                    }
                )
            }
        }
    }
    ///////////

    Row(
        modifier = androidx.compose.ui.Modifier //.padding(10.dp)
            .width(sizeRow.width.dp)
            .height(IntrinsicSize.Min)
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
                    position = position5SeekBar,
                    duration = duration.collectAsState(),
                    onNewProgress = { },
                    onDragStart = { },
                    onDragEnd = {
                        position5SeekBar = it.toInt()
                    }
                )
            }
        }

        Column(
            modifier = Modifier.padding(0.dp, 10.dp)
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Transparent)
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
                    position = position6SeekBar,
                    duration = duration.collectAsState(),
                    onNewProgress = { },
                    onDragStart = { },
                    onDragEnd = {
                        position6SeekBar = it.toInt()
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
                    position = position7SeekBar,
                    duration = duration.collectAsState(),
                    onNewProgress = { },
                    onDragStart = { },
                    onDragEnd = {
                        position7SeekBar = it.toInt()
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
                    position = position8SeekBar,
                    duration = duration.collectAsState(),
                    onNewProgress = { },
                    onDragStart = { },
                    onDragEnd = {
                        position8SeekBar = it.toInt()
                    }
                )
            }
        }
    }
}


@Composable
fun justBar() {
    Column(
        modifier = Modifier.padding(0.dp, 1.dp)
            .weight(1f)
            .height(120.dp)
            .background(Color.Red)
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