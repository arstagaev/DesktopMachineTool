package utils

import java.math.RoundingMode
import java.text.DecimalFormat

fun rndTo2deci(num : Float) : Float{
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING

    return (df.format(num)).replace(",",".").toFloat()

}