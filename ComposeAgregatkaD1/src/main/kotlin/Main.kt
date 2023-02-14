// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import parsing_excel.readExcelFile
import serial_port.initSerialCommunication
import ui.main_screen.timeOfMeasure
import utils.*
import kotlin.concurrent.fixedRateTimer


fun main() = singleWindowApplication (
    title = "Агрегатка Tech v.1.1.4",
    state = WindowState(size = DpSize(1000.dp, 800.dp)),
    visible = true, onKeyEvent = {
        it.key.nativeKeyCode
        true
    }
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

fun startTimer() {
    fixedRateTimer("timer_2", daemon = true, 0L,1000L) {

        timeOfMeasure.value += 1
    }
}