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
import kotlinx.coroutines.flow.MutableStateFlow
import ui.parts_of_screen.center.support_elements.seekbarSetup
import ui.parts_of_screen.textStateMax
import ui.parts_of_screen.textStateMin


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
                    GaugeView2(200,pressure1, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                }
                Box {
                    GaugeView2(200,pressure1, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                }
                Box {
                    GaugeView2(200,pressure1, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                }
                Box {
                    GaugeView2(200,pressure1, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                }
            }
            Row {
                GaugeView2(200,pressure1, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                GaugeView2(200,pressure1, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                GaugeView2(200,pressure1, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
                GaugeView2(200,pressure1, textStateMax.value.text.toInt(), textStateMin.value.text.toInt())
            }

            seekbarSetup(
                pressure1,
                pressure2,
                pressure3,
                pressure4,
                pressure5,
                pressure6,
                pressure7,
                pressure8,
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
