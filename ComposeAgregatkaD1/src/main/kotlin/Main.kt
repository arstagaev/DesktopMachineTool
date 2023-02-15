// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import parsing_excel.targetParseScenario
import serial_port.initSerialCommunication
import serial_port.startReceiveFullData
import storage.createNeededFolders
import storage.createDemoConfigFile
import storage.readParameters
import utils.*
import java.io.File
import javax.swing.JFileChooser
import kotlin.concurrent.fixedRateTimer


fun main() = singleWindowApplication (
    title = "Агрегатка Tech v.1.1.4",
    state = WindowState(size = DpSize(1000.dp, 800.dp)),
    visible = true
) {
    var crtxscp = rememberCoroutineScope().coroutineContext
    //COM_PORT = "COM10"//getComPorts_Array().get(0).systemPortName
    //readExcelFile()

    //var initParameters = readParameters(Dir4MainConfig)


    createNeededFolders()
    initialize(readParameters(Dir4MainConfig))
    CoroutineScope(crtxscp).launch {
        targetParseScenario(createDemoConfigFile())
    }

    App()

    CoroutineScope(Dispatchers.IO).launch {

        initSerialCommunication()
        startReceiveFullData()
    }

//    val properties: Properties = Properties()
//    properties.load(App::class.java.getResourceAsStream("/version.properties"))
//    System.out.println(properties.getProperty("version"))
//    initSerialCommunication("COM3")
}

fun startTimer() {
    fixedRateTimer("timer_2", daemon = true, 0L,1000L) {

        //timeOfMeasure.value += 1
    }
}