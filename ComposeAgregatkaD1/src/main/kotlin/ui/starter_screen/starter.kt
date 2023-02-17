package ui.starter_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazecast.jSerialComm.SerialPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import parsing_excel.targetParseScenario
import screenNav
import showMeSnackBar
import storage.PickTarget
import storage.openPicker
import storage.refreshParametersJson
import ui.charts.ChartWindowNew
import ui.navigation.Screens
import utils.*


@Composable
fun StarterScreen() {

    var expandedOperator by remember { mutableStateOf(false) }
    var expandedCom by remember { mutableStateOf(false) }
    var expandedBaud by remember { mutableStateOf(false) }
    var visibilitySettings = remember { mutableStateOf(false)}
    var choosenCOM = remember { mutableStateOf(0) }
    var choosenBaud = remember { mutableStateOf(BAUD_RATE) }
    val textState = remember { mutableStateOf(OPERATOR_ID) }
    var listOfOperators = mutableListOf<String>()//loadOperators()

    var crtxscp = rememberCoroutineScope().coroutineContext

    //var remarrayports = remember { arrayOfComPorts }
    LaunchedEffect(true) {

        while (true) {
            arrayOfComPorts = getComPorts_Array() as Array<SerialPort>
            delay(1000)
        }
    }

    Column(Modifier.fillMaxSize().background(Color.Black)) {
        Row(modifier = Modifier.fillMaxSize().weight(2f), horizontalArrangement = Arrangement.Center) {

            //Image("",painter = painterResource("drawable/trs.jpg"))
            Text("TRS | Трансмиссионные системы \n Станок №${7}",
                modifier = Modifier//.fillMaxSize()
                    .padding(4.dp).clickable {
                    //screenNav.value = Screens.MAIN
                }, fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.White, textAlign = TextAlign.Center)
        }
        Row(modifier = Modifier.fillMaxSize().weight(3f).background(Color.DarkGray), horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource("drawable/trs.jpg"),
                contentDescription = null
            )
            Column(Modifier.padding(16.dp)) {

                TextField(colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor =  Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = Color.Green),
                    modifier = Modifier.fillMaxWidth(),
                    value = textState.value,
                    onValueChange = {
                        textState.value = it
                        // work json
                        //OPERATOR_ID = it
                    },
                    textStyle = TextStyle.Default.copy(fontSize = 35.sp)
                )

                Box {
                    Text("OPERATOR ID ⬆️",
                        modifier = Modifier.fillMaxSize().clickable {
                            expandedOperator = true
                        }, fontSize = 20.sp, fontFamily = FontFamily.Monospace, color = Color.White, textAlign = TextAlign.Center)

                    DropdownMenu(
                        modifier = Modifier.background(Color.White),
                        expanded = expandedOperator,
                        onDismissRequest = { expandedOperator = false },
                    ) {
                        repeat(listOfOperators.size) {
                            Text("${listOfOperators[it]}", fontSize=18.sp, modifier = Modifier.fillMaxSize().padding(10.dp).clickable(onClick={}))
                        }
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxSize().weight(3f).padding(10.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.width(200.dp).border(BorderStroke(2.dp, Color.Blue))
                .clickable {
                    refreshParametersJson()
                    isAlreadyReceivedBytesForChart.value = false
                    CoroutineScope(crtxscp).launch {
                        //openPicker(Dir3Scenarios)
                        //targetParseScenario(createDemoConfigFile())

                        if (!targetParseScenario(openPicker(Dir3Scenarios))) {
                            showMeSnackBar("Ошибка при парсинге xls",Color.Red)
                        }else {

                            //doOpen_First_ChartWindow.value = true
                            //isAlreadyReceivedBytesForChart.value = true
                            screenNav.value = Screens.MAIN
                        }
                    }



                }) {
                Text("Open Scenario",
                    modifier = Modifier.padding(4.dp), fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.White, textAlign = TextAlign.Center)
            }

            Box(Modifier.width(200.dp).border(BorderStroke(2.dp, Color.Blue))
                .clickable {
                    CoroutineScope(crtxscp).launch {
                        openPicker(Dir2Reports,PickTarget.PICK_CHART)?.let { chartFileAfterExperiment.value = it }




                    }

                }) {
                Text("Open Chart",
                    modifier = Modifier.padding(4.dp), fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.White, textAlign = TextAlign.Center)
            }

            Box(Modifier.width(200.dp).border(BorderStroke(2.dp, Color.Blue))
                .clickable {
                    visibilitySettings.value = !visibilitySettings.value
                }) {
                Text("Settings",
                    modifier = Modifier.padding(4.dp), fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.White, textAlign = TextAlign.Center)
            }

            Box(Modifier.width(200.dp).border(BorderStroke(2.dp, Color.Blue))
                .clickable {

                }) {
                Text("Quick",
                    modifier = Modifier.padding(4.dp), fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.White, textAlign = TextAlign.Center)
            }
//            Box(Modifier.width(200.dp).border(BorderStroke(2.dp, Color.Blue))
//                .clickable { visibilitySettings.value = !visibilitySettings.value }) {
//                Text("Settings",
//                    modifier = Modifier.padding(4.dp)
//                    , fontSize = 24.sp, fontFamily = FontFamily.Monospace,
//                    color = Color.White, textAlign = TextAlign.Center
//                )
//
//            }
//            Box(Modifier.width(200.dp).border(BorderStroke(2.dp, Color.Blue))) {
//                Text("Quick",
//                    modifier = Modifier.padding(4.dp).clickable {
//                        screenNav.value = Screens.MAIN
//                    }, fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.White, textAlign = TextAlign.Center)
//
//            }
        }


        if(visibilitySettings.value) {
            Row(modifier = Modifier.fillMaxSize().weight(4f).background(Color.DarkGray)) {
                LazyColumn {
                    item {
                        Row {
                            Text("COM Port:",
                                modifier = Modifier.width(200.dp).padding(4.dp).clickable {
                                }, fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.White, textAlign = TextAlign.Center)

                            Box {
                                Text(
                                    if (arrayOfComPorts.isEmpty()) "‼️NO COM PORTS‼️" else arrayOfComPorts[choosenCOM.value].systemPortName,
                                    modifier = Modifier.width(200.dp).padding(4.dp).clickable {
                                        expandedCom = !expandedCom
                                    }, fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.Blue, textAlign = TextAlign.Center)

                                DropdownMenu(
                                    modifier = Modifier.background(Color.White),
                                    expanded = expandedCom,
                                    onDismissRequest = { expandedCom = false },
                                ) {
                                    repeat(arrayOfComPorts.size-1) {
                                        Text("${arrayOfComPorts[it].descriptivePortName}", fontSize=18.sp, modifier = Modifier.fillMaxSize().padding(10.dp)
                                            .clickable(onClick= {
                                                choosenCOM.value = it
                                                COM_PORT = arrayOfComPorts[it].systemPortName
                                                logAct("DropdownMenu click ${COM_PORT}")
                                            }))

                                    }
                                }
                            }

                        }
                    }

                    item {
                        Row {
                            Text("Baud-rate:",
                                modifier = Modifier.width(200.dp).padding(4.dp).clickable {
                                }, fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.White, textAlign = TextAlign.Center)

                            Box {
                                Text(choosenBaud.value.toString(),
                                    modifier = Modifier.width(200.dp).padding(4.dp).clickable {
                                        expandedBaud = !expandedBaud
                                    }, fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Color.Blue, textAlign = TextAlign.Center)

                                DropdownMenu(
                                    modifier = Modifier.background(Color.White),
                                    expanded = expandedBaud,
                                    onDismissRequest = { expandedBaud = false },
                                ) {
                                    Text("38400",   fontSize=18.sp, modifier = Modifier.clickable(onClick= { choosenBaud.value = 38400
                                    BAUD_RATE = choosenBaud.value
                                    })  .fillMaxSize().padding(10.dp))
                                    Text("57600",   fontSize=18.sp, modifier = Modifier.clickable(onClick= { choosenBaud.value = 57600
                                    BAUD_RATE = choosenBaud.value
                                    })  .fillMaxSize().padding(10.dp))
                                    Text("115200",  fontSize=18.sp, modifier = Modifier.clickable(onClick= { choosenBaud.value = 115200
                                    BAUD_RATE = choosenBaud.value
                                    }) .fillMaxSize().padding(10.dp))
                                    Text("128000",  fontSize=18.sp, modifier = Modifier.clickable(onClick= { choosenBaud.value = 128000
                                    BAUD_RATE = choosenBaud.value
                                    }) .fillMaxSize().padding(10.dp))
                                    Text("256000",  fontSize=18.sp, modifier = Modifier.clickable(onClick= { choosenBaud.value = 256000
                                    BAUD_RATE = choosenBaud.value
                                    }) .fillMaxSize().padding(10.dp))
                                    Text("500000",  fontSize=18.sp, modifier = Modifier.clickable(onClick= { choosenBaud.value = 500000
                                    BAUD_RATE = choosenBaud.value
                                    }) .fillMaxSize().padding(10.dp))
                                    Text("1000000", fontSize=18.sp, modifier = Modifier.clickable(onClick= { choosenBaud.value = 1000000
                                    BAUD_RATE = choosenBaud.value
                                    }).fillMaxSize().padding(10.dp))
                                    Divider()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}