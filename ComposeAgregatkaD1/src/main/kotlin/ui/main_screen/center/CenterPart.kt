package ui.main_screen.center

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import screenNav
import serial_port.pauseSerialComm
import serial_port.startReceiveFullData
import storage.openPicker
import ui.custom.GaugeX
import ui.main_screen.center.support_elements.solenoidsPanel
import ui.navigation.Screens
import utils.*


@Composable
fun centerPiece(
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

    val ctxScope = CoroutineScope(Dispatchers.IO)


    GlobalScope.launch {
//        firstGaugeData.collect { value ->
//            delay(DELAY_FOR_GET_DATA)
//            //println("|<<<<<<<<<<<<<<<<<<< [${value}]")
//            pressure1X = value
//        }
        dataChunkGauges.collect {
            delay(DELAY_FOR_GET_DATA)
            //println("|<<<<<<<<<<<<<<<<<<< [${it.firstGaugeData}]")
            //longForChart.add(if (pressure1X > 1000) { 1000 } else pressure1X)
            //longForChart.add(pressure1X)

            pressure1X = it.firstGaugeData
            pressure2X = it.secondGaugeData
            pressure3X = it.thirdGaugeData
            pressure4X = it.fourthGaugeData
            pressure5X = it.fifthGaugeData
            pressure6X = it.sixthGaugeData
            pressure7X = it.seventhGaugeData
            pressure8X = it.eighthGaugeData
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

                Text("ATRS.RU", modifier = Modifier.padding(top = (10).dp,start = 20.dp).clickable {
                          screenNav.value = Screens.STARTER
                }, fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Blue
                )
                Text("Scenario:", modifier = Modifier.padding(top = (10).dp,start = 20.dp)
                    , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
                )
                Box(Modifier.clickable {
                    // launch provider
                    openPicker(Dir3Scenarios)
                }) {
                    Text("▶", modifier = Modifier.align(Alignment.TopCenter).padding(top = (10).dp,start = 20.dp)
                        , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Green
                    )
                }
                Box(Modifier.clickable {
                    //stop scenario
                }) {
                    Text("⏸", modifier = Modifier.align(Alignment.TopCenter).padding(top = (10).dp,start = 20.dp)
                        , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
                    )
                }

                Text("Data Live:", modifier = Modifier.padding(top = (10).dp,start = 20.dp)
                    , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
                )
                Box(Modifier.clickable {
                    ctxScope.launch {
                        startReceiveFullData()
                    }

                }) {
                    Text("▶", modifier = Modifier.align(Alignment.TopCenter).padding(top = (10).dp,start = 20.dp)
                        , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Green
                    )
                }
                Box(Modifier.clickable {
                    ctxScope.launch {
                        pauseSerialComm()
                    }

                }) {
                    Text("⏸", modifier = Modifier.align(Alignment.TopCenter).padding(top = (10).dp,start = 20.dp)
                        , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
                    )
                }
                Text("${COM_PORT},${BAUD_RATE}", modifier = Modifier.padding(top = (10).dp,start = 20.dp)
                    , fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.DarkGray
                )
            }
            Spacer(Modifier.fillMaxWidth().height(10.dp))
            Row {
                GaugeX(DpSize(200.dp,200.dp),pressure1X, ( pressures[0].minValue),(pressures[0].maxValue.toInt()),type ="Бар",displayName = pressures[0].displayName, comment = pressures[0].commentString)
                GaugeX(DpSize(200.dp,200.dp),pressure2X, ( pressures[1].minValue),(pressures[1].maxValue.toInt()),type ="Бар",displayName = pressures[1].displayName, comment = pressures[1].commentString)
                GaugeX(DpSize(200.dp,200.dp),pressure3X, ( pressures[2].minValue),(pressures[2].maxValue.toInt()),type ="Бар",displayName = pressures[2].displayName, comment = pressures[2].commentString)
                GaugeX(DpSize(200.dp,200.dp),pressure4X, ( pressures[3].minValue),(pressures[3].maxValue.toInt()),type ="Бар",displayName = pressures[3].displayName, comment = pressures[3].commentString)
            }
            Row {
                GaugeX(DpSize(200.dp,200.dp),pressure5X, ( pressures[4].minValue),(pressures[4].maxValue.toInt()),type = "Бар",displayName = pressures[4].displayName, comment = pressures[4].commentString)
                GaugeX(DpSize(200.dp,200.dp),pressure6X, ( pressures[5].minValue),(pressures[5].maxValue.toInt()),type = "Бар",displayName = pressures[5].displayName, comment = pressures[5].commentString)
                GaugeX(DpSize(200.dp,200.dp),pressure7X, ( pressures[6].minValue),(pressures[6].maxValue.toInt()),type = "Бар",displayName = pressures[6].displayName, comment = pressures[6].commentString)
                GaugeX(DpSize(200.dp,200.dp),pressure8X, ( pressures[7].minValue),(pressures[7].maxValue.toInt()),type = "Бар",displayName = pressures[7].displayName, comment = pressures[7].commentString)
            }
            solenoidsPanel(
                sizeRow, duration
            )

        }
    }
}
