package parsing_bytes

import androidx.compose.ui.graphics.Color
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import crtx1
import initSerialCommunication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import serialPort
import showMeSnackBar
import stopSerialCommunication
import ui.parts_of_screen.timeOfMeasure
import utils.*
import kotlin.concurrent.fixedRateTimer

var pressures = ByteArray(16)
var currents  = ByteArray(16)

private var newData = ByteArray(16)

var COUNTER = 0L
var prs4 = 0
var cur1 = 0
private val DEBUG_PARSING = false

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
    var start_time = 0L
    var incr = 0


    serialPort.addDataListener(object : SerialPortDataListener {
        override fun getListeningEvents(): Int {
            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE
        }

        override fun serialEvent(event: SerialPortEvent) {
            if (incr == 0) {
                start_time = System.currentTimeMillis()
            }
            incr++
            if ((System.currentTimeMillis() - start_time) >= 1000) {
                incr = 0
            }
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
            var dch: DataChunkG? = null

            if (event.eventType == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                event.serialPort.readBytes(updData, 16L)
                //serialPort.readBytes(updData, 16)
                println("> ${updData[0]} ${updData[15]} [size:${updData.size}] ${incr}")
                //serialPort.flushIOBuffers()
                //serialPort
                //event.serialPort.readBytes(updData,16L,16)

                val FST_CNDN = 16
                when {
                    //currency
                    updData[1] >= FST_CNDN && updData[3] >= FST_CNDN && updData[5] >= FST_CNDN && updData[7] >= FST_CNDN -> {
                        var dch = DataChunkG(
                            onesAndTens(byteToInt(updData[0]).toUInt() , byteToInt(updData[1]).toUInt()-16u),
                            onesAndTens(byteToInt(updData[2]).toUInt() , byteToInt(updData[3]).toUInt()-16u),
                            onesAndTens(byteToInt(updData[4]).toUInt() , byteToInt(updData[5]).toUInt()-16u),
                            onesAndTens(byteToInt(updData[6]).toUInt() , byteToInt(updData[7]).toUInt()-16u),

                            onesAndTens(byteToInt( updData[8]).toUInt() , byteToInt(updData[9] ).toUInt()-16u),
                            onesAndTens(byteToInt(updData[10]).toUInt(),  byteToInt(updData[11]).toUInt()-16u),
                            onesAndTens(byteToInt(updData[12]).toUInt(),  byteToInt(updData[13]).toUInt()-16u),
                            onesAndTens(byteToInt(updData[14]).toUInt(),  byteToInt(updData[15]).toUInt()-16u)
                        )

                        if (DEBUG_PARSING) {
                            arrCurrRaw.add(updData)

                            arrCurr.add(arrayListOf(
                                dch.firstGaugeData,
                                dch.secondGaugeData,
                                dch.thirdGaugeData,
                                dch.fourthGaugeData,
                                dch.fifthGaugeData,
                                dch.sixthGaugeData,
                                dch.seventhGaugeData,
                                dch.eighthGaugeData
                            ))
                        }


                    }
                    //pressure
                    updData[1] < FST_CNDN && updData[3] < FST_CNDN && updData[5] < FST_CNDN && updData[7] < FST_CNDN -> {
                        //println("> ${updData.toHexString()} [size:${updData.size}]")

                        dch = DataChunkG(
                            onesAndTens(byteToInt(updData[0]).toUInt() , byteToInt(updData[1]).toUInt()),
                            onesAndTens(byteToInt(updData[2]).toUInt() , byteToInt(updData[3]).toUInt()),
                            onesAndTens(byteToInt(updData[4]).toUInt() , byteToInt(updData[5]).toUInt()),
                            onesAndTens(byteToInt(updData[6]).toUInt() , byteToInt(updData[7]).toUInt()),

                            onesAndTens(byteToInt( updData[8]).toUInt() , byteToInt(updData[9] ).toUInt()),
                            onesAndTens(byteToInt(updData[10]).toUInt(),  byteToInt(updData[11]).toUInt()),
                            onesAndTens(byteToInt(updData[12]).toUInt(),  byteToInt(updData[13]).toUInt()),
                            onesAndTens(byteToInt(updData[14]).toUInt(),  byteToInt(updData[15]).toUInt())
                        )

                        //logGarbage(">>> ${dch.toString()}")

                        CoroutineScope(crtx1).launch {
                            dataChunkGauges.emit(dch)
                            //firstGaugeData  .emit(dch.firstGaugeData)
                        }

                        if (DEBUG_PARSING) {
                            arrPressRaw.add(updData)

                            arrPress.add(arrayListOf(
                                dch.firstGaugeData,
                                dch.secondGaugeData,
                                dch.thirdGaugeData,
                                dch.fourthGaugeData,
                                dch.fifthGaugeData,
                                dch.sixthGaugeData,
                                dch.seventhGaugeData,
                                dch.eighthGaugeData
                            ))
                        }
                    }
                    else -> {
                        // if not valid numbers - refresh connection
                        initSerialCommunication()
                    }

                }

                if (DEBUG_PARSING) {
                    // print clear results:
                    if (arrPressRaw.size > 9) {
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

                        repeat(arrCurr.size) {
                            println(arrCurr[it])
                        }
                        repeat(arrPress.size) {
                            println(arrPress[it])
                        }
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
                    //dataChunkGauges.emit(dch!!)

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