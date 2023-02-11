package utils

import java.math.RoundingMode
import java.text.DecimalFormat

fun rndTo2deci(num : Float) : Float{
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN

    return (df.format(num)).replace(",",".").toFloat()

}


fun Int.to2ByteArray() : ByteArray = byteArrayOf(toByte(), shr(8).toByte())
