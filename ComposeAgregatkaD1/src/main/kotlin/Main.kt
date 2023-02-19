// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.flow.MutableSharedFlow
import storage.readParameters
import ui.charts.ChartWindowNew
import ui.windows.WindowTypes
import utils.*
import kotlin.concurrent.fixedRateTimer


fun main() = singleWindowApplication (
    title = "Агрегатка Tech v.1.1.4",
    state = WindowState(size = DpSize(1000.dp, 800.dp)),
    visible = true
) {
    var crtxscp = rememberCoroutineScope().coroutineContext
    val windowFocusRequestSharedFlow = remember { MutableSharedFlow<WindowTypes>() }
    val doOpenNewWindowInternal = remember { doOpen_First_ChartWindow }
    val doOpenNewWindowInternal2 = remember { doOpen_Second_ChartWindow }
    //COM_PORT = "COM10"//getComPorts_Array().get(0).systemPortName
    //readExcelFile()

    //var initParameters = readParameters(Dir4MainConfig)


    initialize(readParameters(Dir4MainConfig_Txt))

    var isHaveConn = false
    getComPorts_Array()?.forEach {
        if (it.systemPortName == COM_PORT) {
            isHaveConn = true
        }
    }
    if (!isHaveConn) {
        showMeSnackBar("NO Connect to ${COM_PORT} !!", Color.Red)
    }


    App()
    if (doOpenNewWindowInternal.value && isAlreadyReceivedBytesForChart.value) {
        ChartWindowNew(withStandard = true).chartWindow()
        //chartWindow()
    }
    if (doOpenNewWindowInternal2.value) {
        ChartWindowNew(withStandard = true).chartWindow()
        //chartWindow()
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