package parsing_bytes

import androidx.compose.ui.graphics.Color
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import initSerialCommunication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import serialPort
import showMeSnackBar
import stopSerialCommunication
import ui.parts_of_screen.timeOfMeasure
import utils.DataChunkG
import utils.onesAndTens
import utils.toHexString
import kotlin.concurrent.fixedRateTimer

var pressures = ByteArray(16)
var currents  = ByteArray(16)

private var newData = ByteArray(16)

var COUNTER = 0L
var prs4 = 0
var cur1 = 0

fun startTimer() {
    fixedRateTimer("timer_2", daemon = false, 0L,1L) {

        //timeOfMeasure.value += 1
        COUNTER++
        //println("pip")
    }
}

fun parseBytesCallback() {
    println("Initialize listener parseBytesCallback")
    var arrCurrRaw  = arrayListOf<ByteArray>()
    var arrPressRaw = arrayListOf<ByteArray>()

    var arrCurr =  arrayListOf<ArrayList<Int>>()
    var arrPress = arrayListOf<ArrayList<Int>>()

    serialPort.addDataListener(object : SerialPortDataListener {
        override fun getListeningEvents(): Int {
            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE
        }

        override fun serialEvent(event: SerialPortEvent) {
//            if (event.eventType == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
//                //return
//                try {
//                    serialPort.readBytes(newData,15)
//                }catch (e: Exception) {
//                    println("error: ${e.message}")
//                }
//            }
            //>>>>
            //val newData0 = ByteArray(serialPort.bytesAvailable())
            //val numRead: Int = serialPort.readBytes(newData, newData0.size.toLong())
            //println("Read $numRead bytes.")

            //newData = ByteArray(serialPort.bytesAvailable())
            //println("Inside that listener, what happening")
            var updData = ByteArray(16)

            if (event.eventType == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                serialPort.readBytes(updData, 16)

                println("> ${updData.toHexString()} [size:${updData.size}]")
                // >= 16  - четные  currents
                // <= 15 - pressure
                // четные в условии если нет - то фиговый пакет

                //println("|----------------------------------|")

                //vtory bytes
                // >= 16 tok
                // если в интервал не входит то сбрасываем
                // MAIN PARSER:

                var dch = DataChunkG(
                    onesAndTens(updData[0].toUInt() ,updData[1].toUInt()).toInt(),
                    onesAndTens(updData[2].toUInt() ,updData[3].toUInt()).toInt(),
                    onesAndTens(updData[4].toUInt() ,updData[5].toUInt()).toInt(),
                    onesAndTens(updData[6].toUInt() ,updData[7].toUInt()).toInt(),

                    onesAndTens( updData[8].toUInt() ,updData[9].toUInt()).toInt(),
                    onesAndTens(updData[10].toUInt(),updData[11].toUInt()).toInt(),
                    onesAndTens(updData[12].toUInt(),updData[13].toUInt()).toInt(),
                    onesAndTens(updData[14].toUInt(),updData[15].toUInt()).toInt()
                )
                println(">> ${dch.toString()}")

                val FST_CNDN = 16
                when {
                    updData[1] >= FST_CNDN && updData[3] >= FST_CNDN && updData[5] >= FST_CNDN && updData[7] >= FST_CNDN -> {
                        arrCurrRaw.add(updData)

                        arrCurr.add(arrayListOf(dch.firstGaugeData,dch.secondGaugeData,dch.thirdGaugeData,dch.fourthGaugeData,dch.fifthGaugeData,dch.sixthGaugeData,dch.seventhGaugeData,dch.eighthGaugeData))

                    }
                    updData[1] < FST_CNDN && updData[3] < FST_CNDN && updData[5] < FST_CNDN && updData[7] < FST_CNDN -> {
                        arrPressRaw.add(updData)

                        arrPress.add(arrayListOf(dch.firstGaugeData,dch.secondGaugeData,dch.thirdGaugeData,dch.fourthGaugeData,dch.fifthGaugeData,dch.sixthGaugeData,dch.seventhGaugeData,dch.eighthGaugeData))

                    }
                    else -> {
                        // if not valid numbers - refresh connection
                        initSerialCommunication()
                    }

                }


                // print clear results:
                if (arrCurrRaw.size > 9) {
                    stopSerialCommunication()
                    println("_______current:")
                    repeat(arrCurrRaw.size) {
                        println(arrCurrRaw[it].toHexString())
                    }
                    println("_______pressure:")
                    repeat(arrPressRaw.size) {
                        println(arrPressRaw[it].toHexString())
                    }
                    ///
                    println("********************************")
                    return
                    repeat(arrCurr.size) {
                        println(arrCurr[it])
                    }
                    repeat(arrPress.size) {
                        println(arrPress[it])
                    }
                }
            }
            pressures = ByteArray(16)
            currents = ByteArray(16)
            COUNTER = 0L
            // println("###>>> ${newData?.joinToString()}")
            //println(">>> ${newData}")
            if (newData == null)
                return


            try {
                CoroutineScope(Dispatchers.IO).launch {

                    //dataChunkGauges.emit(dch)
//                firstGaugeData.emit(  onesAndTens(newData[0],newData[1]).toInt())
//                secondGaugeData.emit( onesAndTens(newData[2],newData[3]).toInt())
//                thirdGaugeData.emit(  onesAndTens(newData[4],newData[5]).toInt())
//                fourthGaugeData.emit( onesAndTens(newData[6],newData[7]).toInt())
//                fifthGaugeData.emit(  onesAndTens(newData[8],newData[9]).toInt())
//                sixthGaugeData.emit(  onesAndTens(newData[10],newData[11]).toInt())
//                seventhGaugeData.emit(onesAndTens(newData[12],newData[13]).toInt())
//                eighthGaugeData.emit( onesAndTens(newData[14],newData[15]).toInt())

//                firstGaugeData.emit(onesAndTens(newData[0],newData[1]).toInt())
//                secondGaugeData.emit(onesAndTens(newData[2],newData[3]).toInt())
//                thirdGaugeData.emit(onesAndTens(newData[4],newData[5]).toInt())
//                fourthGaugeData.emit(onesAndTens(newData[6],newData[7]).toInt())
//                fifthGaugeData.emit(onesAndTens(newData[8],newData[9]).toInt())
//                sixthGaugeData.emit(onesAndTens(newData[10],newData[11]).toInt())
//                seventhGaugeData.emit(onesAndTens(newData[12],newData[13]).toInt())
//                eighthGaugeData.emit(onesAndTens(newData[14],newData[15]).toInt())
                }
            } catch (exc : Exception) {
                showMeSnackBar("${exc.message}", Color.Red)
            }

//            firstAnalog( filterKalman( ))
//            secondAnalog(onesAndTens(newData[2],newData[3]))
//            thirdAnalog( onesAndTens(newData[4],newData[5]))
//            fourthAnalog(onesAndTens(newData[6],newData[7]))
//
//            fiveAnalog( onesAndTens(newData[8],newData[9]))
//            sixAnalog(  onesAndTens(newData[10],newData[11]))
//            sevenAnalog(onesAndTens(newData[12],newData[13]))
//            eightAnalog(onesAndTens(newData[14],newData[15]))
//
        }
    })

    timeOfMeasure.value = 0L
    startTimer()
}

fun isPacketCompletable() : Boolean {
    if (prs4 > 4 || cur1 > 1) {
        println("BROKEN PACKET ")
        return false
    }else {
        return true
    }

}