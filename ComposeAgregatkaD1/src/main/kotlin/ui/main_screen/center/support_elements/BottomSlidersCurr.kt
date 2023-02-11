package ui.main_screen.center.support_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import map
import parsing_excel.solenoids
import serial_port.writeToSerialPort
import ui.styles.fontDigital
import utils.*

@Composable
fun currentPanelSetup(
    sizeRow: Size,
    duration: MutableStateFlow<Long>
) {
    var crctx = rememberCoroutineScope().coroutineContext

    var position1SeekBar by remember { mutableStateOf(-1) }
    var position2SeekBar by remember { mutableStateOf(-1) }
    var position3SeekBar by remember { mutableStateOf(-1) }
    var position4SeekBar by remember { mutableStateOf(-1) }

    var position5SeekBar by remember { mutableStateOf(-1) }
    var position6SeekBar by remember { mutableStateOf(-1) }
    var position7SeekBar by remember { mutableStateOf(-1) }
    var position8SeekBar by remember { mutableStateOf(-1) }
//    if (solenoids.size<5){
//        showMeSnackBar("Excel error",Color.Red)
//    }else {
//        showMeSnackBar("Excel config parse success",Color.White)
//    }
    CoroutineScope(crctx).launch {
        dataChunkCurrents.collect {
            delay(DELAY_FOR_GET_DATA)
            //println("|currrr [${it.firstCurrentData}]")
            //longForChart.add(if (pressure1X > 1000) { 1000 } else pressure1X)
            //longForChart.add(pressure1X)

            position1SeekBar = it.firstCurrentData
            position2SeekBar = it.secondCurrentData
            position3SeekBar = it.thirdCurrentData
            position4SeekBar = it.fourthCurrentData

            position5SeekBar = it.fifthCurrentData
            position6SeekBar = it.sixthCurrentData
            position7SeekBar = it.seventhCurrentData
            position8SeekBar = it.eighthCurrentData
        }
    }

    Row(
        modifier = Modifier //.padding(10.dp)
            .width(sizeRow.width.dp)
            .height(IntrinsicSize.Min)
            .background(Color.White)
    ) {

            justBar(index = 1,solenoids[0].displayName, current = map(x=position1SeekBar,in_min=0, in_max = 4095, out_min=0, out_max = solenoids[0].currentMaxValue), maxPWM = solenoids[0].maxPWM, step = solenoids[0].step, duration)
            justBar(index = 2,solenoids[1].displayName, current = map(x=position2SeekBar,in_min=0, in_max = 4095, out_min=0, out_max = solenoids[1].currentMaxValue), maxPWM = solenoids[1].maxPWM, step = solenoids[1].step, duration)
            justBar(index = 3,solenoids[2].displayName, current = map(x=position3SeekBar,in_min=0, in_max = 4095, out_min=0, out_max = solenoids[2].currentMaxValue), maxPWM = solenoids[2].maxPWM, step = solenoids[2].step, duration)
            justBar(index = 4,solenoids[3].displayName, current = map(x=position4SeekBar,in_min=0, in_max = 4095, out_min=0, out_max = solenoids[3].currentMaxValue), maxPWM = solenoids[3].maxPWM, step = solenoids[3].step, duration)

//            justBar(solenoids[4].displayName, position5SeekBar, duration)
//            justBar(solenoids[5].displayName, position6SeekBar, duration)
//            justBar(solenoids[6].displayName, position7SeekBar, duration)
//            justBar(solenoids[7].displayName, position8SeekBar, duration)

    }
    Row(
        modifier = androidx.compose.ui.Modifier //.padding(10.dp)
            .width(sizeRow.width.dp)
            .height(IntrinsicSize.Min)
            .background(Color.White)
    ) {
        justBar(index = 5,solenoids[4].displayName, current = map(x=position5SeekBar,in_min=0, in_max = 4095, out_min=0, out_max = solenoids[0].currentMaxValue), maxPWM = solenoids[4].maxPWM, step = solenoids[4].step, duration)
        justBar(index = 6,solenoids[5].displayName, current = map(x=position6SeekBar,in_min=0, in_max = 4095, out_min=0, out_max = solenoids[0].currentMaxValue), maxPWM = solenoids[5].maxPWM, step = solenoids[5].step, duration)
        justBar(index = 7,solenoids[6].displayName, current = map(x=position7SeekBar,in_min=0, in_max = 4095, out_min=0, out_max = solenoids[0].currentMaxValue), maxPWM = solenoids[6].maxPWM, step = solenoids[6].step, duration)
        justBar(index = 8,solenoids[7].displayName, current = map(x=position8SeekBar,in_min=0, in_max = 4095, out_min=0, out_max = solenoids[0].maxPWM), maxPWM = solenoids[7].maxPWM, step = solenoids[7].step, duration)

    }



}

var asd1 = mutableStateOf(byteArrayOf(0x71,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00))
var asd2 = mutableStateOf(byteArrayOf(0x51,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00))

var ch1 = 0x00.toByte()
var ch2 = 0x00.toByte()
var ch3 = 0x00.toByte()
var ch4 = 0x00.toByte()

var ch5 = 0x00.toByte()
var ch6 = 0x00.toByte()
var ch7 = 0x00.toByte()
var ch8 = 0x00.toByte()

@Composable
fun justBar(index: Int, channelName : String, current: Int, maxPWM: Int, step: Int,  duration: MutableStateFlow<Long>,) {
    var pos = remember { mutableStateOf(0.5f) }
    var increment = remember { mutableStateOf(0)}
    var maxPWMremember = remember { mutableStateOf(maxPWM) }
        Column(
            modifier = Modifier.padding(0.dp, 1.dp)
                .width(200.dp)
                .height(90.dp)
                .background(Color.Black)
                .padding(5.dp)
            //.fillMaxWidth()
        ) {
            var position1SeekBar by remember { mutableStateOf(10) }

            Row(
                modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.Black),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Gray).weight(1f).clickable {
                        maxPWMremember.value = 0
                        //pos.value = 0.0f
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize().padding(vertical = 20.dp).align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        text = "<<",
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Gray).weight(2f).clickable {
                        maxPWMremember.value = maxPWMremember.value - step
                        if (maxPWMremember.value < 0) {
                            maxPWMremember.value = 0
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            selector(index, maxPWMremember.value.to2ByteArray()[0])
                            writeToSerialPort(byteArrayOf(0x71,ch1, 0x00,ch2, 0x00,ch3, 0x00,ch4),false, delay = 100L)
                            delay(100)
                            writeToSerialPort(byteArrayOf(0x51,ch5, 0x00,ch6, 0x00,ch7, 0x00,ch8),false, delay = 0L)
                        }
                        //pos.value-= 0.1f
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize().padding(vertical = 20.dp).align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        text = "-",
                        color = Color.White
                    )
                }


                Spacer(modifier = Modifier.width(10.dp).fillMaxHeight())
                Column(Modifier.fillMaxSize().weight(5f)) {
                    Text(
                        "${channelName}",
                        modifier = Modifier.fillMaxSize().weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 8.sp,
                        color = Color.White
                    )
                    Text(
                        "${current}",
                        modifier = Modifier.fillMaxSize().weight(1f),
                        fontFamily = fontDigital,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Column(Modifier.fillMaxSize().weight(5f)) {
                    Text(
                        "PWM",
                        modifier = Modifier.fillMaxSize().weight(1f),
                        fontSize = 8.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Text(
                        "${((maxPWMremember.value.toFloat() * 100f) / 255f).toInt()}",
                        modifier = Modifier.fillMaxSize().weight(1f),
                        fontFamily = fontDigital,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp).fillMaxHeight())


                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Gray).weight(2f).clickable {
                        maxPWMremember.value = maxPWMremember.value + step
                        if (maxPWMremember.value > 255) {
                            maxPWMremember.value = 255
                        }
                        println("WELL ${(maxPWMremember.value.toFloat() * 100f) / 255f}")

                        CoroutineScope(Dispatchers.IO).launch {
                            selector(index, maxPWMremember.value.to2ByteArray()[0])
                            writeToSerialPort(byteArrayOf(0x71,ch1, 0x00,ch2, 0x00,ch3, 0x00,ch4),false, delay = 100L)
                            delay(100)
                            writeToSerialPort(byteArrayOf(0x51,ch5, 0x00,ch6, 0x00,ch7, 0x00,ch8),false, delay = 0L)
                        }
                        //pos.value += 0.1f
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize().padding(vertical = 20.dp).align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        text = "+",
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Gray).weight(1f).clickable {
                        maxPWMremember.value = 255
                        //pos.value = 1.0f
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize().padding(vertical = 20.dp).align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        text = ">>",
                        color = Color.White
                    )
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth().height(20.dp)
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    progress = ((maxPWMremember.value.toFloat() * 100f) / 255f)/100f //rndTo2deci(pos.value)
                )
            }
        }
}

private fun selector(chIndex: Int, byte: Byte) {
    when(chIndex) {
        1 -> ch1 = byte
        2 -> ch2 = byte
        3 -> ch3 = byte
        4 -> ch4 = byte
        5 -> ch5 = byte
        6 -> ch6 = byte
        7 -> ch7 = byte
        8 -> ch8 = byte
    }
}

@Composable
fun SimpleProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float = 0.7f,
    progressBarColor: Color = Color.Red,
    cornerRadius: Dp = 0.dp,
    trackColor: Color = Color(0XFFFBE8E8),
    thumbRadius: Dp = 0.dp,
    thumbColor: Color = Color.White,
    thumbOffset: Dp = thumbRadius
) {}