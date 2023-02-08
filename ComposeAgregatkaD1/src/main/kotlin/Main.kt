// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import kotlinx.coroutines.*
import parsing_bytes.parseBytesCallback
import ui.parts_of_screen.center.onesAndTens
import ui.parts_of_screen.speedOfPort
import ui.parts_of_screen.timeOfMeasure
import utils.*
import kotlin.concurrent.fixedRateTimer


fun main() = singleWindowApplication (
    title = "Агрегатка Tech v.1.1.3",
    state = WindowState(size = DpSize(1000.dp, 800.dp)),
    visible = false
) {
    COM_PORT = "COM10"//getComPorts_Array().get(0).systemPortName
    //readExcelFile()
    //App()
    initSerialCommunication()


//    val properties: Properties = Properties()
//    properties.load(App::class.java.getResourceAsStream("/version.properties"))
//    System.out.println(properties.getProperty("version"))
    //initSerialCommunication("COM3")
}

var serialPort: SerialPort = SerialPort.getCommPort(COM_PORT)


fun initSerialCommunication() {
    repeat(
        getComPorts_Array().size
    ) {
        println(">>>Available Com ports:${getComPorts_Array().get(it).systemPortName} is Open: ${getComPorts_Array().get(it).isOpen}||${getComPorts_Array().get(it).descriptivePortName}")
    }

    println(">>>serial communication has been started, COM_PORT:${COM_PORT}")
    serialPort = SerialPort.getCommPort(COM_PORT)
    //SerialPort.getCommPorts()


    serialPort.baudRate = speedOfPort.value.text.toInt()

    serialPort.setComPortParameters(speedOfPort.value.text.toInt(),8,1,SerialPort.NO_PARITY)
    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0)
    serialPort.openPort()
    showMeSnackBar("baudRate of Port:${speedOfPort.value.text.toInt()} ", Color.White)
    var a = 0
    val sendBytes = byteArrayOf(0x74.toByte(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
//    try {
//        Thread.sleep(2000)
//    } catch (e: InterruptedException) {
//        e.printStackTrace()
//    }
    GlobalScope.launch {
        delay(1000)
        println("Run Callbacks::")

        parseBytesCallback()

        repeat(1) {
            println("Run Send bytes:: ${sendBytes.toHexString()}   size of bytes: ${sendBytes.size}")
            serialPort.writeBytes(sendBytes, 1)
            //println("goo " + sendBytes.size)
        }
    }





}



fun stopSerialCommunication(){
    serialPort.removeDataListener()
    serialPort.closePort()

    println(">< STOP SERIAL PORT // is Open:${serialPort.isOpen}")
}

