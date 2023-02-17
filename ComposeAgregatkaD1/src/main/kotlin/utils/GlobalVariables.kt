package utils

import androidx.compose.runtime.mutableStateOf
import com.fazecast.jSerialComm.SerialPort
import enums.StateExperiments
import enums.StateParseBytes
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import parsing_excel.models.PressuresHolder
import parsing_excel.models.ScenarioStep
import parsing_excel.models.SolenoidHolder
import ui.charts.Pointer
import ui.charts.SaverChart
import java.io.File
import javax.swing.JFileChooser

var COM_PORT = "COM3"
var BAUD_RATE = 115200
var OPERATOR_ID = "no name"
var DELAY_FOR_GET_DATA = 0L
var arrayOfComPorts = arrayOf<SerialPort>()


val Dir1Configs = File("${JFileChooser().fileSystemView.defaultDirectory.toString()}\\agregatka_machinetool\\config")
val Dir2Reports = File("${JFileChooser().fileSystemView.defaultDirectory.toString()}\\agregatka_machinetool\\reports")
val Dir3Scenarios = File("${JFileChooser().fileSystemView.defaultDirectory.toString()}\\agregatka_machinetool\\scenarios")
val Dir4MainConfig_Json = File(Dir1Configs,"\\config.json")
val Dir4MainConfig_Txt = File(Dir1Configs,"\\config.txt")
val Dir5Operators = File(Dir1Configs,"\\operator_ids.txt")

val Dir6 = File(Dir2Reports,"\\demo.txt")
val Dir7 = File("${JFileChooser().fileSystemView.defaultDirectory.toString()}\\agregatka_machinetool\\reports\\standard")
val Dir8 = File(Dir7,"\\stndrd.txt")


var solenoids = mutableListOf<SolenoidHolder>()
var pressures = mutableListOf<PressuresHolder>()
var scenario = mutableListOf<ScenarioStep>()

var GLOBAL_STATE = mutableStateOf(StateParseBytes.INIT)
var STATE_CHART = mutableStateOf(StateExperiments.NONE)

var recordData = arrayListOf<SaverChart>()


var dataChunkGauges   = MutableSharedFlow<DataChunkG>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
var dataChunkCurrents = MutableSharedFlow<DataChunkCurrent>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

val PRESSURE_MAX_RAW = 4095
val CURRENT_MAX_RAW = 255

var arr1Measure = arrayListOf<Pointer>()
var arr2Measure = arrayListOf<Pointer>()
var arr3Measure = arrayListOf<Pointer>()
var arr4Measure = arrayListOf<Pointer>()
var arr5Measure = arrayListOf<Pointer>()
var arr6Measure = arrayListOf<Pointer>()
var arr7Measure = arrayListOf<Pointer>()
var arr8Measure = arrayListOf<Pointer>()

var limitTime = 4500

var isAlreadyReceivedBytesForChart = mutableStateOf(false)
var doOpen_First_ChartWindow = mutableStateOf(false)

var chartFileAfterExperiment: File = File(Dir2Reports,"demo.txt")

data class DataChunkG(
    var firstGaugeData:   Int,
    var secondGaugeData:  Int,
    var thirdGaugeData:   Int,
    var fourthGaugeData:  Int,
    var fifthGaugeData:   Int,
    var sixthGaugeData:   Int,
    var seventhGaugeData: Int,
    var eighthGaugeData:  Int
    )

data class DataChunkCurrent(
    var firstCurrentData: Int,
    var secondCurrentData: Int,
    var thirdCurrentData: Int,
    var fourthCurrentData: Int,
    var fifthCurrentData: Int,
    var sixthCurrentData: Int,
    var seventhCurrentData: Int,
    var eighthCurrentData: Int
)