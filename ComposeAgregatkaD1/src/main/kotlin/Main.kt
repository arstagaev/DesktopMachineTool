// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.fazecast.jSerialComm.SerialPort
import kotlinx.coroutines.*
import serial_port.parseBytesCallback
import parsing_excel.readExcelFile
import serial_port.initSerialCommunication
import ui.parts_of_screen.speedOfPort
import utils.*



fun main() = singleWindowApplication (
    title = "Агрегатка Tech v.1.1.3",
    state = WindowState(size = DpSize(1000.dp, 800.dp)),
    visible = true
) {
    COM_PORT = "COM10"//getComPorts_Array().get(0).systemPortName
    readExcelFile()
    App()
    initSerialCommunication()


//    val properties: Properties = Properties()
//    properties.load(App::class.java.getResourceAsStream("/version.properties"))
//    System.out.println(properties.getProperty("version"))
//    initSerialCommunication("COM3")
}









