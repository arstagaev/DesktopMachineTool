package utils

import androidx.compose.runtime.mutableStateOf
import com.fazecast.jSerialComm.SerialPort
import enums.State
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import parsing_excel.models.PressuresHolder
import parsing_excel.models.ScenarioStep
import parsing_excel.models.SolenoidHolder
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
val Dir4MainConfig = File(Dir1Configs,"\\config.json")
val Dir5Operators = File(Dir1Configs,"\\operator_ids.txt")

var solenoids = mutableListOf<SolenoidHolder>()
var pressures = mutableListOf<PressuresHolder>()
var scenario = mutableListOf<ScenarioStep>()

var GLOBAL_STATE = mutableStateOf(State.INIT)


var longForChart   = arrayListOf<Int>()

var dataChunkGauges   = MutableSharedFlow<DataChunkG>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
var dataChunkCurrents = MutableSharedFlow<DataChunkCurrent>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

val PRESSURE_MAX_RAW = 4095
val CURRENT_MAX_RAW = 255

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