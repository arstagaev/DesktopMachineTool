package ui.parts_of_screen.center

import GaugeView2
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ui.parts_of_screen.center.support_elements.currentPanelSetup
import ui.parts_of_screen.textStateMax
import ui.parts_of_screen.textStateMin
import utils.DELAY_FOR_GET_DATA
import utils.dataChunkTrans


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
    var pressure1X by remember { mutableStateOf(1) }
    var pressure2X by remember { mutableStateOf(1) }
    var pressure3X by remember { mutableStateOf(1) }
    var pressure4X by remember { mutableStateOf(1) }
    var pressure5X by remember { mutableStateOf(1) }
    var pressure6X by remember { mutableStateOf(1) }
    var pressure7X by remember { mutableStateOf(1) }
    var pressure8X by remember { mutableStateOf(1) }
    val duration = MutableStateFlow(100L)
    GlobalScope.launch {
//        firstGaugeData.collect { value ->
//            delay(DELAY_FOR_GET_DATA)
//            println("|<<<<<<<<<<<<<<<<<<< [${value}]")
//            pressure1X = value
//        }
        dataChunkTrans.collect {
            delay(DELAY_FOR_GET_DATA)
            //println("|<<<<<<<<<<<<<<<<<<< [${it.firstGaugeData}]")
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
                .background(Color.White)
                .layoutId("r")
                .onGloballyPositioned { coordinates ->
                    sizeRow = coordinates.size.toSize()
                }
        ) {
            Row {

                Box {
                    GaugeView2(200,pressure1X, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                }
                Box {
                    GaugeView2(200,pressure2X, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                }
                Box {
                    GaugeView2(200,pressure3X, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                }
                Box {
                    GaugeView2(200,pressure4X, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                }
            }
            Row {
                GaugeView2(200,pressure5X, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                GaugeView2(200,pressure6X, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                GaugeView2(200,pressure7X, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                GaugeView2(200,pressure8X, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
            }

            currentPanelSetup(
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                sizeRow, duration
            )

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
