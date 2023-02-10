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
import parsing_excel.pressures
import ui.parts_of_screen.center.support_elements.currentPanelSetup
import ui.parts_of_screen.textStateMin
import utils.DELAY_FOR_GET_DATA
import utils.dataChunkGauges
import utils.firstGaugeData
import utils.longForChart


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
    var pressure1X by remember { mutableStateOf(0) }
    var pressure2X by remember { mutableStateOf(0) }
    var pressure3X by remember { mutableStateOf(0) }
    var pressure4X by remember { mutableStateOf(0) }
    var pressure5X by remember { mutableStateOf(0) }
    var pressure6X by remember { mutableStateOf(0) }
    var pressure7X by remember { mutableStateOf(0) }
    var pressure8X by remember { mutableStateOf(0) }
    val duration = MutableStateFlow(100L)
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
                .background(Color.White)
                .layoutId("r")
                .onGloballyPositioned { coordinates ->
                    sizeRow = coordinates.size.toSize()
                }
        ) {
            Row {
                GaugeView2(200,pressure1X, (pressures[0].maxValue.toInt()), (if (textStateMin.value.text.toInt() >= 0) { textStateMin.value.text.toInt() } else pressures[0].minValue), pressures[0].displayName, units = "Bar",comment = "In the above example, the value of the variable good is inserted at good in the templateGood string. Likewise, the value of the variable great is inserted at great in the templateGreat string.")
                GaugeView2(200,pressure2X, (pressures[1].maxValue.toInt()), (if (textStateMin.value.text.toInt() >= 0) { textStateMin.value.text.toInt() } else pressures[1].minValue), pressures[1].displayName)
                GaugeView2(200,pressure3X, (pressures[2].maxValue.toInt()), (if (textStateMin.value.text.toInt() >= 0) { textStateMin.value.text.toInt() } else pressures[2].minValue), pressures[2].displayName)
                GaugeView2(200,pressure4X, (pressures[3].maxValue.toInt()), (if (textStateMin.value.text.toInt() >= 0) { textStateMin.value.text.toInt() } else pressures[3].minValue), pressures[3].displayName)
            }
            Row {
                GaugeView2(200,pressure5X, (pressures[4].maxValue.toInt()), if (textStateMin.value.text.toInt() >= 0) { textStateMin.value.text.toInt() } else pressures[4].minValue, pressures[4].displayName)
                GaugeView2(200,pressure6X, (pressures[5].maxValue.toInt()), if (textStateMin.value.text.toInt() >= 0) { textStateMin.value.text.toInt() } else pressures[5].minValue, pressures[5].displayName)
                GaugeView2(200,pressure7X, (pressures[6].maxValue.toInt()), if (textStateMin.value.text.toInt() >= 0) { textStateMin.value.text.toInt() } else pressures[6].minValue, pressures[6].displayName)
                GaugeView2(200,pressure8X, (pressures[7].maxValue.toInt()), if (textStateMin.value.text.toInt() >= 0) { textStateMin.value.text.toInt() } else pressures[7].minValue, pressures[7].displayName)
            }

            currentPanelSetup(
                sizeRow, duration
            )

        }
    }
}

fun sclble() {

}

//fun cnvrtMin(raw: Int): Int {
//
//}
//
//fun cnvrtMax(raw: Int): Int {
//
//}
