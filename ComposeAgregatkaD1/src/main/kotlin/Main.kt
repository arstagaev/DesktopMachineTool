// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import parsing.readExcelFile
import ui.parts_of_screen.center.onesAndTens
import ui.parts_of_screen.speedOfPort
import ui.parts_of_screen.textCOMPORT
import ui.parts_of_screen.timeOfMeasure
import utils.*
import kotlin.concurrent.fixedRateTimer


fun main() = singleWindowApplication (
    title = "Агрегатка Tech v.1.1.3", state = WindowState(size = DpSize(1000.dp, 800.dp))
) {
    getCommaports()
    readExcelFile()
    App()
    initSerialCommunication()

//    val properties: Properties = Properties()
//    properties.load(App::class.java.getResourceAsStream("/version.properties"))
//    System.out.println(properties.getProperty("version"))
    //initSerialCommunication("COM3")
}

var serialPort: SerialPort = SerialPort.getCommPort("COM3")
private var newData = ByteArray(16)

var pressures = ByteArray(16)
var currents  = ByteArray(16)

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

fun initSerialCommunication() {

    serialPort = SerialPort.getCommPort(textCOMPORT.value.text.toString())
    //SerialPort.getCommPorts()
    println(">>>serial communication has been started")

    serialPort.baudRate = speedOfPort.value.text.toInt()

    serialPort.setComPortParameters(speedOfPort.value.text.toInt(),8,1,SerialPort.NO_PARITY)
    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0)
    serialPort.openPort()
    showMeSnackBar("baudRate of Port:${speedOfPort.value.text.toInt()} ", Color.White)
    var a = 0
    val sendBytes = byteArrayOf(0x74.toByte(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    try {
        Thread.sleep(2000)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    while (a < 1) {
        serialPort.writeBytes(sendBytes, 1)

        //            try {
        println("goo " + sendBytes.size)
        //                serialPort.getOutputStream().write(bb);
//                //serialPort.getOutputStream().flush();
//            } catch (IOException e) {
//                System.out.println("pizdec "+e.getMessage());
//                e.printStackTrace();
//            }
        a++
    }
    timeOfMeasure.value = 0L
    startTimer()


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

            //newData = ByteArray(serialPort.bytesAvailable())
            var updData = ByteArray(16)
            val CB = 16
            val CB2 = 15
            if (event.eventType == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                serialPort.readBytes(updData, 16)
                // >= 16  - четные  currents
                // <= 15 - pressure
                // четные в условии если нет - то фиговый пакет
                println("input: ${updData[1] } ${updData[3]} ${updData[5]} ${updData[7]} ${updData[9]} ${updData[11]} ${updData[13]} ${updData[15]}")
                if( updData[1]>= CB && updData[3]>= CB && updData[5]>= CB && updData[7]>= CB && updData[9]>= CB && updData[11]>= CB && updData[13]>= CB && updData[15]>= CB ){
                    //println("###currents: ${updData?.joinToString()}")
                    cur1++
                    prs4 = 0
                    currents = updData



                }else if (updData[1]+updData[3] != 0 && updData[1] <= CB2 && updData[3]<= CB2 && updData[5]<= CB2 && updData[7]<= CB2 && updData[9]<= CB2 && updData[11]<= CB2 && updData[13]<= CB2 && updData[15]<= CB2 ) {
                    //println(">>>pressures: ${updData?.joinToString()}")
                    prs4++
                    cur1 = 0
                    pressures = updData



                }else {
                    // here is analyzer of broken chunk/packet
                    println("BROKEN PACKET ")
                }



//                if (cnt4 < 4) {
//
//                    println("### ${updData?.joinToString()}")
//                    cnt4++
//                } else if (cnt4 == 4) {
//                    cnt4 = 0
//                    println(">>> ${updData?.joinToString()}")
//
//                }
//                if (!isPacketCompletable()) {
//
//                }else {
//
//                }
                if (pressures.isNotEmpty() && pressures.sum() != 0) {
                    println("pressures: ${pressures.joinToString()}")
                }
                if (currents.isNotEmpty() && currents.sum() != 0) {
                    println("currents:  ${currents.joinToString()}")
                }
                println("|_________________________________|")
            }
            pressures = ByteArray(16)
            currents = ByteArray(16)
            COUNTER = 0L
           // println("###>>> ${newData?.joinToString()}")
            //println(">>> ${newData}")
            if (newData == null)
                return


//            if ( newData[1] >= 15 ){
//
//            }else {
//
//            }
            // MAIN PARSER:
//            var dch = DataChunkG(
//                onesAndTens(newData[0] ,newData[1]).toInt(),
//                onesAndTens(newData[2] ,newData[3]).toInt(),
//                onesAndTens(newData[4] ,newData[5]).toInt(),
//                onesAndTens(newData[6] ,newData[7]).toInt(),
//
//                onesAndTens(newData[8] ,newData[9]).toInt(),
//                onesAndTens(newData[10],newData[11]).toInt(),
//                onesAndTens(newData[12],newData[13]).toInt(),
                onesAndTens(newData[14],newData[15]).toInt()
//            )
            //println("> ${dch.toString()}")
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
                showMeSnackBar("${exc.message}",Color.Red)
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
}

fun isPacketCompletable() : Boolean {
    if (prs4 > 4 || cur1 > 1) {
        println("BROKEN PACKET ")
        return false
    }else {
        return true
    }

}

fun stopSerialCommunication(){
    serialPort.removeDataListener()
    serialPort.closePort()

    println(">< STOP SERIAL PORT // is Open:${serialPort.isOpen}")
}

