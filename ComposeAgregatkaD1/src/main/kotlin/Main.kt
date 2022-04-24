// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import kotlinx.coroutines.GlobalScope
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

//    val properties: Properties = Properties()
//    properties.load(App::class.java.getResourceAsStream("/version.properties"))
//    System.out.println(properties.getProperty("version"))
    //initSerialCommunication("COM3")
}

var serialPort: SerialPort = SerialPort.getCommPort("COM3")
private var newData : ByteArray? = null



fun startTimer() {
    fixedRateTimer("timer_2", daemon = false, 0L,10) {

        timeOfMeasure.value += 1

    }
}

fun initSerialCommunication(COMPORT_NUMBER: String) {

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
    while (a < 10) {
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
            if (event.eventType != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                //sound(-1)
                return
            }
            newData = ByteArray(serialPort.bytesAvailable())

            if (newData == null)
                return

            val numRead = serialPort.readBytes(newData, newData!!.size.toLong())

            //println("conv " + (newData.toUByteArray()).joinToString() + "[[ ${newData.size}")
            //curPoint =
            println(">> ${onesAndTens(newData!![0] ,newData!![1]).toInt()}")
            // MAIN PARSER:
            var dch = DataChunkG(
                onesAndTens(newData!![0] ,newData!![1]).toInt(),
                onesAndTens(newData!![2] ,newData!![3]).toInt(),
                onesAndTens(newData!![4] ,newData!![5]).toInt(),
                onesAndTens(newData!![6] ,newData!![7]).toInt(),

                onesAndTens(newData!![8] ,newData!![9]).toInt(),
                onesAndTens(newData!![10],newData!![11]).toInt(),
                onesAndTens(newData!![12],newData!![13]).toInt(),
                onesAndTens(newData!![14],newData!![15]).toInt()
            )
            //println("> ${dch.toString()}")
            try {
                GlobalScope.launch {
                    dataChunkGauges.emit(dch)
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

fun stopSerialCommunication(){
    serialPort.removeDataListener()
    serialPort.closePort()

    println(">< STOP SERIAL PORT // is Open:${serialPort.isOpen}")
}

