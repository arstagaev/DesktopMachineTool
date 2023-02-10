package utils

import androidx.compose.runtime.mutableStateOf
import enums.State
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

var COM_PORT = "COM3"
var DELAY_FOR_GET_DATA = 0L

var GLOBAL_STATE = mutableStateOf(State.INIT)

var firstGaugeData  = MutableSharedFlow<Int>()
var secondGaugeData = MutableSharedFlow<Int>()
var thirdGaugeData  = MutableSharedFlow<Int>()
var fourthGaugeData = MutableSharedFlow<Int>()
var fifthGaugeData  = MutableSharedFlow<Int>()
var sixthGaugeData  = MutableSharedFlow<Int>()
var seventhGaugeData  = MutableSharedFlow<Int>()
var eighthGaugeData = MutableSharedFlow<Int>()

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