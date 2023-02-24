package ui.main_screen.center

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import enums.ExplorerMode
import enums.StateExperiments
import enums.StateParseBytes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import serial_port.*
import storage.createMeasureExperiment
import ui.charts.Pointer
import ui.custom.GaugeX
import ui.main_screen.center.support_elements.solenoidsPanel
import utils.*


@Composable
fun CenterPiece(
) {
    var sizeRow by remember { mutableStateOf(Size.Zero) }
    var pressure1X by remember { mutableStateOf(0) }
    var pressure2X by remember { mutableStateOf(0) }
    var pressure3X by remember { mutableStateOf(0) }
    var pressure4X by remember { mutableStateOf(0) }
    var pressure5X by remember { mutableStateOf(0) }
    var pressure6X by remember { mutableStateOf(0) }
    var pressure7X by remember { mutableStateOf(0) }
    var pressure8X by remember { mutableStateOf(0) }
    val duration = MutableStateFlow(100L)

    val stateChart = remember { STATE_EXPERIMENT }
    val explMode = remember { EXPLORER_MODE }
    val expandedCom = remember { mutableStateOf(false) }


    val txt = remember { txtOfScenario }

    val ctxScope =
        CoroutineScope(Dispatchers.IO) + rememberCoroutineScope().coroutineContext + CoroutineName("MainScreen-CenterPart")

    LaunchedEffect(true) {
        ctxScope.launch {
            //EXPLORER_MODE.value = ExplorerMode.MANUAL
            //reInitSolenoids()
            indexOfScenario.value = 0

            sound_On()
            startReceiveFullData()
            comparatorToSolenoid(indexOfScenario.value)
            sendScenarioToController()
            var count = 0
            dataChunkGauges.collect {
                if (it.isExperiment) {
                    count++
                }
                //delay(DELAY_FOR_GET_DATA)
                //logGarbage("Exp>  ${STATE_CHART.value.name}||${arr1Measure.size} ${dataChunkGauges.replayCache.size} ${solenoids.size} ${pressures.size} ${scenario.size}")

                //println("|<<<<<<<<<<<<<<<<<<<${it.isExperiment} [${it.firstGaugeData}]")
                //longForChart.add(if (pressure1X > 1000) { 1000 } else pressure1X)
                //longForChart.add(pressure1X)

                pressure1X = map(it.firstGaugeData, 0, 4095, (pressures[0].minValue), (pressures[0].maxValue),)
                pressure2X = map(it.secondGaugeData, 0, 4095, (pressures[1].minValue), (pressures[1].maxValue),)
                pressure3X = map(it.thirdGaugeData, 0, 4095, (pressures[2].minValue), (pressures[2].maxValue),)
                pressure4X = map(it.fourthGaugeData, 0, 4095, (pressures[3].minValue), (pressures[3].maxValue),)
                pressure5X = map(it.fifthGaugeData, 0, 4095, (pressures[4].minValue), (pressures[4].maxValue),)
                pressure6X = map(it.sixthGaugeData, 0, 4095, (pressures[5].minValue), (pressures[5].maxValue),)
                pressure7X = map(it.seventhGaugeData, 0, 4095, (pressures[6].minValue), (pressures[6].maxValue),)
                pressure8X = map(it.eighthGaugeData, 0, 4095, (pressures[7].minValue), (pressures[7].maxValue),)

                when (EXPLORER_MODE.value) {
                    ExplorerMode.AUTO -> {
                        //logGarbage("konec ${}")
                        if (
                            //limitTime >= incrementTime &&
                            (it.isExperiment)
                        ) {


                            arr1Measure.add(Pointer(x = incrementTime, y = pressure1X)) //it.firstGaugeData, ))
                            arr2Measure.add(Pointer(x = incrementTime, y = pressure2X)) //it.secondGaugeData,))
                            arr3Measure.add(Pointer(x = incrementTime, y = pressure3X)) //it.thirdGaugeData, ))
                            arr4Measure.add(Pointer(x = incrementTime, y = pressure4X)) //it.fourthGaugeData,))
                            arr5Measure.add(Pointer(x = incrementTime, y = pressure5X)) //it.fifthGaugeData, ))
                            arr6Measure.add(Pointer(x = incrementTime, y = pressure6X)) //it.sixthGaugeData, ))
                            arr7Measure.add(Pointer(x = incrementTime, y = pressure7X)) //it.seventhGaugeData))
                            arr8Measure.add(Pointer(x = incrementTime, y = pressure8X)) //it.eighthGaugeData, ))

//                            num = scenario[indexScenario].time
//
//                            if (num > 0) {
//                                // 2 - is recieve data every 2ms
//                                num -= 2
//                            } else {
//                                indexScenario++
//                                num = scenario[indexScenario].time
//                                txt.value = scenario[indexScenario].text
//                            }
                            incrementTime += 2
                            //test_time += 2

                        } else if (STATE_EXPERIMENT.value == StateExperiments.PREP_DATA) {
                            logGarbage("Output: |${incrX}|=>|${count}|  | ${arr1Measure.size} ${arr1Measure[arr1Measure.lastIndex]}")

                            STATE_EXPERIMENT.value = StateExperiments.PREPARE_CHART
                            incrementTime = 0
                            if (!isAlreadyReceivedBytesForChart.value) {
                                isAlreadyReceivedBytesForChart.value = true
                                createMeasureExperiment()
                            }


                        }
                    }

                    ExplorerMode.MANUAL -> {
                        // without recording
                    }
                }
            }

//            dataChunkGauges.collect {
//
//
//            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {

        Column(
            modifier = Modifier //.padding(10.dp)
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
                .background(Color.Black)
                .layoutId("r")
                .onGloballyPositioned { coordinates ->
                    sizeRow = coordinates.size.toSize()
                }
        ) {
            Row() {
//                Box(Modifier.size(40.dp)) {
//                    Image(painterResource("/trs.jpg"),"")
//                }
                if (isExperimentStarts.value) {
                    Text(
                        "Rec...",
                        modifier = Modifier.padding(top = (10).dp, start = 20.dp).clickable {
                        },
                        fontFamily = FontFamily.Default,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
                Text(
                    "${txt.value}",
                    modifier = Modifier.width(90.dp).padding(top = (10).dp, start = 20.dp).clickable {
                        //screenNav.value = Screens.STARTER
                    },
                    fontFamily = FontFamily.Default,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                Box(Modifier.clickable {
                    expandedCom.value = !expandedCom.value
                }) {
                    Text(
                        "Mode: ${explMode.value.name}",
                        modifier = Modifier.padding(top = (10).dp, start = 20.dp),
                        fontFamily = FontFamily.Default, fontSize = 20.sp,
                        fontWeight = FontWeight.Bold, color = Color.White
                    )

                    DropdownMenu(
                        modifier = Modifier.background(Color.White),
                        expanded = expandedCom.value,
                        onDismissRequest = { expandedCom.value = false },
                    ) {
                        Text(
                            "AUTO", fontSize = 18.sp, modifier = Modifier.fillMaxSize().padding(10.dp)
                                .clickable(onClick = {
                                    EXPLORER_MODE.value = ExplorerMode.AUTO
                                }), color = Color.Black
                        )
                        Text(
                            "MANUAL", fontSize = 18.sp, modifier = Modifier.fillMaxSize().padding(10.dp)
                                .clickable(onClick = {
                                    EXPLORER_MODE.value = ExplorerMode.MANUAL
                                }), color = Color.Black
                        )
                    }
                }

                Box(Modifier.clickable {
                    test_time = 0
                    // launch
                    if (explMode.value == ExplorerMode.AUTO) {
                        if (STATE_EXPERIMENT.value != StateExperiments.START) {
                            ctxScope.launch {
                                writeToSerialPort(
                                    byteArrayOf(
                                        0x78,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00,
                                        0x00
                                    ), withFlush = false
                                )
                            }

//                            if (GLOBAL_STATE.value != StateParseBytes.PLAY) {
//                                ctxScope.launch {
//
//                                    initSerialCommunication()
//                                }
//                            }

                            GLOBAL_STATE.value = StateParseBytes.PLAY
                            sound_On()
                            logGarbage("ONON ${test_time} V")
                            test_time = 0

                            indexOfScenario.value = 0
                            indexScenario = 0
                            num = scenario[indexScenario].time
                            isAlreadyReceivedBytesForChart.value = false
                            logGarbage("ONON ${test_time} A")

                        } else {
                            sound_Error()
                        }
                    } else if (explMode.value == ExplorerMode.MANUAL) {
                        indexOfScenario.value--
                        ctxScope.launch {

                            comparatorToSolenoid(indexOfScenario.value)
                        }
                        scenario.getOrNull(indexOfScenario.value)?.let { txtOfScenario.value = it.text }
                        //txtOfScenario.value = scenario.getOrNull(indexOfScenario.value)?.text
                        //txtOfScenario.value = scenario[indexOfScenario.value].text
                    }


                }) {
                    Text(
                        if (explMode.value == ExplorerMode.AUTO) "▶" else "⏪",
                        modifier = Modifier.align(Alignment.TopCenter).padding(top = (10).dp, start = 20.dp),
                        fontFamily = FontFamily.Default,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Box(Modifier.clickable {
                    //stop scenario

                    CoroutineScope(Dispatchers.IO).launch {
                        if (explMode.value == ExplorerMode.AUTO) {
                            reInitSolenoids()
                            GLOBAL_STATE.value = StateParseBytes.WAIT
//                            initSerialCommunication()
//                            startReceiveFullData()
                        } else if (explMode.value == ExplorerMode.MANUAL) {
                            indexOfScenario.value++
                            comparatorToSolenoid(indexOfScenario.value)

                            //txtOfScenario.value = scenario.getOrElse(indexOfScenario.value) { 0 }
                            scenario.getOrNull(indexOfScenario.value)?.let { txtOfScenario.value = it.text }
                            //txtOfScenario.value = scenario.getOrElse(indexOfScenario.value) { scenario[0] }.text
                        }
                    }
                }) {
                    Text(
                        if (explMode.value == ExplorerMode.AUTO) "⏸" else "⏩",
                        modifier = Modifier.align(Alignment.TopCenter).padding(top = (10).dp, start = 20.dp),
                        fontFamily = FontFamily.Default,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                /**
                 *  DATA LIVE HERE:
                 */
                Text("Data Live:", modifier = Modifier.padding(top = (10).dp,start = 20.dp)
                    , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
                )
                Box(Modifier.clickable {
//                    ctxScope.launch {
//
//                        if (GLOBAL_STATE.value != StateParseBytes.PLAY) {
//                            GLOBAL_STATE.value = StateParseBytes.PLAY
//
//
//                        } else {
//                            indexOfScenario.value = 0
//                            reInitSolenoids()
//                            sound_Error()
//                        }
//
//                    }

                }) {
                    Text("▶", modifier = Modifier.align(Alignment.TopCenter).padding(top = (10).dp,start = 20.dp)
                        , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Green
                    )
                }
                Box(Modifier.clickable {
                    ctxScope.launch {
                        GLOBAL_STATE.value = StateParseBytes.WAIT
                        pauseSerialComm()
                    }

                }) {
                    Text("⏸", modifier = Modifier.align(Alignment.TopCenter).padding(top = (10).dp,start = 20.dp)
                        , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
                    )
                }
                Text("${COM_PORT},${BAUD_RATE},${limitTime}ms", modifier = Modifier.padding(top = (10).dp,start = 20.dp)
                    , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.DarkGray
                )
            }
                Spacer(Modifier.fillMaxWidth().height(10.dp))
                Row {
                    GaugeX(
                        DpSize(200.dp, 200.dp),
                        pressure1X,
                        (pressures[0].minValue),
                        (pressures[0].maxValue.toInt()),
                        type = "Бар",
                        displayName = pressures[0].displayName,
                        comment = pressures[0].commentString
                    )
                    GaugeX(
                        DpSize(200.dp, 200.dp),
                        pressure2X,
                        (pressures[1].minValue),
                        (pressures[1].maxValue.toInt()),
                        type = "Бар",
                        displayName = pressures[1].displayName,
                        comment = pressures[1].commentString
                    )
                    GaugeX(
                        DpSize(200.dp, 200.dp),
                        pressure3X,
                        (pressures[2].minValue),
                        (pressures[2].maxValue.toInt()),
                        type = "Бар",
                        displayName = pressures[2].displayName,
                        comment = pressures[2].commentString
                    )
                    GaugeX(
                        DpSize(200.dp, 200.dp),
                        pressure4X,
                        (pressures[3].minValue),
                        (pressures[3].maxValue.toInt()),
                        type = "Бар",
                        displayName = pressures[3].displayName,
                        comment = pressures[3].commentString
                    )
                }
                Row {
                    GaugeX(
                        DpSize(200.dp, 200.dp),
                        pressure5X,
                        (pressures[4].minValue),
                        (pressures[4].maxValue.toInt()),
                        type = "Бар",
                        displayName = pressures[4].displayName,
                        comment = pressures[4].commentString
                    )
                    GaugeX(
                        DpSize(200.dp, 200.dp),
                        pressure6X,
                        (pressures[5].minValue),
                        (pressures[5].maxValue.toInt()),
                        type = "Бар",
                        displayName = pressures[5].displayName,
                        comment = pressures[5].commentString
                    )
                    GaugeX(
                        DpSize(200.dp, 200.dp),
                        pressure7X,
                        (pressures[6].minValue),
                        (pressures[6].maxValue.toInt()),
                        type = "Бар",
                        displayName = pressures[6].displayName,
                        comment = pressures[6].commentString
                    )
                    GaugeX(
                        DpSize(200.dp, 200.dp),
                        pressure8X,
                        (pressures[7].minValue),
                        (pressures[7].maxValue.toInt()),
                        type = "Бар",
                        displayName = pressures[7].displayName,
                        comment = pressures[7].commentString
                    )
                }
                solenoidsPanel(sizeRow, duration)
            }
        }
}
