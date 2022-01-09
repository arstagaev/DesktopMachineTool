import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.skia.Font
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.TextLine
import ui.parts_of_screen.textDelay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun GaugeView2(input_SIZE_ALL : Int, PRESSURE_Input: Int, maxValue: Int, minValue: Int) {
    var SIZE_ALL = input_SIZE_ALL
    var angle = 0f
    if (PRESSURE_Input <= maxValue) {
        if (PRESSURE_Input <= minValue) {
            angle = 0f
        } else {
            angle = (270f* PRESSURE_Input) / maxValue
        }
    }else {
        angle = 270f
    }


    val path = Path()
    val scope = rememberCoroutineScope()
    val animatedPercentage = remember { Animatable(angle.toFloat(), Float.VectorConverter) }
    var FONT_SIZE_SCALE = 0.07f * SIZE_ALL


    LaunchedEffect(key1 = angle) {
        scope.launch(Dispatchers.Main) {
            animatedPercentage.animateTo(
                targetValue = angle.toFloat(),
                animationSpec = tween(
                    durationMillis = textDelay.value.text.toInt(),
                    easing = FastOutLinearInEasing
//                    easing = {
//                        BounceInterpolator().getInterpolation(it)
//                    }
                )
            )
        }
    }

    //var WIDTH = size

    Box(
        modifier = Modifier.size(SIZE_ALL.dp).border(BorderStroke(2.dp, Color.Gray)),
        contentAlignment = Alignment.Center
    ) {
//        val typeFace = org.jetbrains.skia.Typeface.makeFromName("TimesRoman", FontStyle.BOLD)
        Text("${PRESSURE_Input}", modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(10.dp)
            //.offset(calcNumGaug(90f,WIDTH).x.dp,calcNumGaug(90f,WIDTH).y.dp)
            , fontFamily = FontFamily.Default, fontSize = 30.sp, fontWeight = FontWeight.Bold
        )
//        Text("120389", modifier = Modifier.offset(calcNumGaug(180f,WIDTH).x.dp,calcNumGaug(180f,WIDTH).y.dp))
//        Text("120389", modifier = Modifier.offset(calcNumGaug(225f,WIDTH).x.dp,calcNumGaug(225f,WIDTH).y.dp))
//        Text("120389", modifier = Modifier.offset(calcNumGaug(270f,WIDTH).x.dp,calcNumGaug(270f,WIDTH).y.dp))
//        Text("120389", modifier = Modifier.offset(calcNumGaug(315f,WIDTH).x.dp,calcNumGaug(315f,WIDTH).y.dp))
//        Text("120389", modifier = Modifier.offset(calcNumGaug(0f,WIDTH).x.dp,calcNumGaug(0f,WIDTH).y.dp))
//        Text("120389", modifier = Modifier.offset(calcNumGaug(45f,WIDTH).x.dp,calcNumGaug(45f,WIDTH).y.dp))

        Canvas(
            modifier = Modifier
                .padding(2.dp)
                .size(SIZE_ALL.dp).border(BorderStroke(4.dp, Color.DarkGray))
        ) {


//            drawBackgroundIndicatorsByLevel(
//                path,
//                size,
//                gaugeWidth,
//                animatedPercentage.value + 170f
//            )

//            for (i in 0..4) {
//                drawBackgroundIndicators(path, size, i, gaugeWidth)
//            }
            val canvasSize = size
            val canvasWidth = size.width
            val canvasHeight = size.height

            //println("## h ${canvasHeight} w ${canvasWidth}")
//            drawRect(
//                color = Color.Gray,
//                topLeft = Offset(x = 0F, y = 0F),
//                size = this.size
//            )

            SIZE_ALL = canvasWidth.toInt()

            drawCircle(
                color = Color.Green,
                radius = 8.dp.toPx(),
                center = Offset(calcNumGaug(135f,SIZE_ALL).x,calcNumGaug(135f,SIZE_ALL).y)
            )
            drawCircle( // small
                color = Color.Green,
                radius = 4.dp.toPx(),
                center = Offset(calcNumGaug(157.5f,SIZE_ALL).x,calcNumGaug(157.5f,SIZE_ALL).y)
            )
            drawCircle(
                color = Color.Green,
                radius = 8.dp.toPx(),
                center = Offset(calcNumGaug(180f,SIZE_ALL).x,calcNumGaug(180f,SIZE_ALL).y)
            )
            drawCircle( // small
                color = Color.Green,
                radius = 4.dp.toPx(),
                center = Offset(calcNumGaug(202.5f,SIZE_ALL).x,calcNumGaug(202.5f,SIZE_ALL).y)
            )
            drawCircle(
                color = Color.Green,
                radius = 8.dp.toPx(),
                center = Offset(calcNumGaug(225f,SIZE_ALL).x,calcNumGaug(225f,SIZE_ALL).y)
            )
            drawCircle( // small
                color = Color.Green,
                radius = 4.dp.toPx(),
                center = Offset(calcNumGaug(247.5f,SIZE_ALL).x,calcNumGaug(247.5f,SIZE_ALL).y)
            )
            drawCircle(
                color = Color.Green,
                radius = 8.dp.toPx(),
                center = Offset(calcNumGaug(270f,SIZE_ALL).x,calcNumGaug(270f,SIZE_ALL).y)
            )
            drawCircle( // small
                color = Color.Green,
                radius = 4.dp.toPx(),
                center = Offset(calcNumGaug(292.5f,SIZE_ALL).x,calcNumGaug(292.5f,SIZE_ALL).y)
            )
            drawCircle(
                color = Color.Green,
                radius = 8.dp.toPx(),
                center = Offset(calcNumGaug(315f,SIZE_ALL).x,calcNumGaug(315f,SIZE_ALL).y)
            )
            drawCircle( // small
                color = Color.Green,
                radius = 4.dp.toPx(),
                center = Offset(calcNumGaug(337.5f,SIZE_ALL).x,calcNumGaug(337.5f,SIZE_ALL).y)
            )
            drawCircle(
                color = Color.Green,
                radius = 8.dp.toPx(),
                center = Offset(calcNumGaug(0f,SIZE_ALL).x  ,calcNumGaug(0f,SIZE_ALL).y)
            )
            drawCircle( // small
                color = Color.Green,
                radius = 4.dp.toPx(),
                center = Offset(calcNumGaug(22.5f,SIZE_ALL).x ,calcNumGaug(22.5f,SIZE_ALL).y)
            )
            drawCircle(
                color = Color.Green,
                radius = 8.dp.toPx(),
                center = Offset(calcNumGaug(45f,SIZE_ALL).x ,calcNumGaug(45f,SIZE_ALL).y)
            )


            val textPaint = Paint().asFrameworkPaint()

            drawIntoCanvas { canvas ->
                val typeFace = org.jetbrains.skia.Typeface.makeFromName("TimesRoman", FontStyle.BOLD)
                canvas.nativeCanvas.drawTextLine(
                    TextLine.Companion.make("${minValue}", Font(typeFace,FONT_SIZE_SCALE)),
                    calcNumGaug(135f,SIZE_ALL).x-13,calcNumGaug(135f,SIZE_ALL).y,
                    textPaint
                )
                canvas.nativeCanvas.drawTextLine(
                    TextLine.Companion.make("${maxValue/6}", Font(typeFace,FONT_SIZE_SCALE)),
                    calcNumGaug(180f,SIZE_ALL).x-13,calcNumGaug(180f,SIZE_ALL).y,
                    textPaint
                )
                canvas.nativeCanvas.drawTextLine(
                    TextLine.Companion.make("${(maxValue*2)/6}", Font(typeFace,FONT_SIZE_SCALE)),
                    calcNumGaug(225f,SIZE_ALL).x-13,calcNumGaug(225f,SIZE_ALL).y,
                    textPaint
                )
                canvas.nativeCanvas.drawTextLine(
                    TextLine.Companion.make("${(maxValue*3)/6}", Font(typeFace,FONT_SIZE_SCALE)),
                    calcNumGaug(270f,SIZE_ALL).x-13,calcNumGaug(270f,SIZE_ALL).y,
                    textPaint
                )
                canvas.nativeCanvas.drawTextLine(
                    TextLine.Companion.make("${(maxValue*4)/6}", Font(typeFace,FONT_SIZE_SCALE)),
                    calcNumGaug(315f,SIZE_ALL).x-13,calcNumGaug(315f,SIZE_ALL).y,
                    textPaint
                )
                if (((maxValue*5)/6)>999) {
                    canvas.nativeCanvas.drawTextLine(
                        TextLine.Companion.make("${(maxValue*5)/6}", Font(typeFace,FONT_SIZE_SCALE)),
                        calcNumGaug(0f,SIZE_ALL).x-18,calcNumGaug(0f,SIZE_ALL).y,
                        textPaint
                    )
                } else {
                    canvas.nativeCanvas.drawTextLine(
                        TextLine.Companion.make("${(maxValue*5)/6}", Font(typeFace,FONT_SIZE_SCALE)),
                        calcNumGaug(0f,SIZE_ALL).x-13,calcNumGaug(0f,SIZE_ALL).y,
                        textPaint
                    )
                }


                canvas.nativeCanvas.drawTextLine(
                    TextLine.Companion.make("${maxValue}", Font(typeFace,FONT_SIZE_SCALE)),
                    calcNumGaug(45f,SIZE_ALL).x-13,calcNumGaug(45f,SIZE_ALL).y,
                    textPaint
                )

//                canvas.nativeCanvas.drawTextLine(
//                    TextLine.make("${maxValue}", Font(typeFace,30f)),
//                    calcNumGaug(90f,WIDTH).x-13,calcNumGaug(90f,WIDTH).y,
//                    textPaint
//                )

            }


            //print("| x= ${X}   y= ${Y} |")




            drawCircle(
                color = Color.Black,
                radius = 4.dp.toPx()
            )


            rotate(degrees = animatedPercentage.value) {
                drawLine(
                    color = Color.Black,
                    start = center,
                    end = Offset(SIZE_ALL*0.233f,SIZE_ALL*0.75f),
                    strokeWidth = 4.dp.toPx()
                )
            }
        }
//        Text(
//            text = (1000*((animatedPercentage.value + 170f) / 1.6f)/100).roundToInt().toString(),
//            modifier = Modifier
//                //.offset(y = (-50).dp)
//                .background(Color.White),
//            fontSize = 15.sp
//        )

    }
}
data class XY(var x : Float, var y : Float)

fun calcNumGaug(angle : Float, size : Int) : XY {

    //        cx cy              R (has been 0.4f for mac i guess)
    var X = (size / 2f  + size * (0.4f) * cos( angle *( PI / 180f ) ) ).toFloat()
    var Y = (size / 2f  + size * (0.4f) * sin( angle *( PI / 180f ) ) ).toFloat()

    //println(" >> ${size}  x:${X} y:${Y} ")
    return XY(X,Y)
}




data class XY_DP(var x : Dp, var y : Dp)
fun calcNumGaugTEST(angle : Float, size : Int) : XY_DP {

    //        cx cy              R (has been 0.4f for mac i guess)
    var X = (size / 2f  + size * (0.4f) * cos( angle *( PI / 180f ) ) ).toFloat()
    var Y = (size / 2f  + size * (0.4f) * sin( angle *( PI / 180f ) ) ).toFloat()

    println(" >> ${size}  x:${X} y:${Y} ")
    return XY_DP(X.dp,Y.dp)
}