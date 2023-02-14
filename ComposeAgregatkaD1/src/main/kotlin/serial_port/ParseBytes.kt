package serial_port

import com.fazecast.jSerialComm.*
import kotlinx.coroutines.*
import utils.*


var pressures = ByteArray(16)
var currents  = ByteArray(16)

private var newData = ByteArray(16)

var prs4 = 0
var cur1 = 0
private val DEBUG_PARSING = false

private val crtx1 = CoroutineName("main")


var startFlag = false
var stopFlag = false


class PacketListener : SerialPortPacketListener {
    override fun getListeningEvents(): Int {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED
    }

    override fun getPacketSize(): Int {
        return 16
    }

    override fun serialEvent(event: SerialPortEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            val newData = event.receivedData
            //println("Received data of size: " + newData.size)
            //for (i in newData.indices) print(Char(newData[i].toUShort()))
            println("${newData.toHexString()}")
            coreParse(newData)
        }

    }
}

fun parseBytesCallback() {
    println("Initialize listener parseBytesCallback")


    CoroutineScope(Dispatchers.IO).launch {

//        val inp: InputStream = serialPort.getInputStream()
//        var btarr = byteArrayOf() //byteArrayOf(0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0)
//        var lastByte: Byte = -1
//
//        var increment = 0
//        try {
//            while (true) {
//                CoroutineScope(Dispatchers.IO).launch {
//                    val byte = inp.read().toByte()
//                    //println("M>>> "+inp.readNBytes(15).toHexString())
//                    if (inp.read() != -1) {
//                        //lastByte = byte
//
//                        if (btarr.size == 16) {
//                            println("bytearr: ${btarr.toHexString()}")
//                            btarr = byteArrayOf()
//
//                            btarr += byte
//                            //btarr[btarr.size-1] = byte
//                        } else {
//                            btarr += byte
//                            //btarr[btarr.size-1] = byte
//                        }
//
//                    }
//
//
//                }
//
//                //inp.available()
//                //inp.readAllBytes()
//            }
//        } catch (e: Exception) {
//            println("exception ${btarr.toHexString()}")
//            inp.close()
//            e.printStackTrace()
//        }
//        return@launch
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
                //>>>>
                //val newData0 = ByteArray(serialPort.bytesAvailable())
                //val numRead: Int = serialPort.readBytes(newData, newData0.size.toLong())
                //println("Read $numRead bytes.")

                //newData = ByteArray(serialPort.bytesAvailable())
                //println("Inside that listener, what happening")
                var updData = ByteArray(16)


                if (event.eventType == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    CoroutineScope(Dispatchers.IO).launch {
                        serialPort.readBytes(updData, 16L)
                        //serialPort.inputStream.
                        //serialPort.readBytes(updData, 16)



                        //serialPort.flushIOBuffers()
                        //serialPort
                        //event.serialPort.readBytes(updData,16L,16)
//                        if (
//                            updData[0] == 0xFE.toByte() && updData[1] == 0xFF.toByte() &&
//                            updData[2] == 0xFE.toByte() && updData[3] == 0xFF.toByte() &&
//                            updData[4] == 0xFE.toByte() && updData[5] == 0xFF.toByte() &&
//                            updData[6] == 0xFE.toByte() && updData[7] == 0xFF.toByte() &&
//                            updData[8] == 0xFE.toByte() && updData[9] == 0xFF.toByte() &&
//                            updData[10] == 0xFE.toByte() && updData[11] == 0xFF.toByte() &&
//                            updData[12] == 0xFE.toByte() && updData[13] == 0xFF.toByte() &&
//                            updData[14] == 0xFE.toByte() && updData[15] == 0xFF.toByte()
//                        ) {
//
//                        }
                        //println(">>> ${updData.toHexString()}")
                        coreParse(updData)
                    }
                }
            }
        })
    }
}




private var arrCurrRaw  = arrayListOf<ByteArray>()
private var arrPressRaw = arrayListOf<ByteArray>()

private var arrCurr =  arrayListOf<ArrayList<Int>>()
private var arrPress = arrayListOf<ArrayList<Int>>()
private var start_time = 0L
private var incr = 0

suspend fun coreParse(updData: ByteArray) = withContext(Dispatchers.IO) {
    var dch: DataChunkG? = null
    var dchCurr: DataChunkCurrent? = null

    if (incr == 0) {
        start_time = System.currentTimeMillis()
    }
    incr++
    val delta = System.currentTimeMillis() - start_time

    if (delta >= 1000) {
        // measure number of packets:
        //println("> ${updData[0]} ${updData[15]} [size:${updData.size}] ${incr} ]-[ ${delta} ms")
        incr = 0
    }

    val FST_CNDN = 16

    when {

        //currency
        updData[1] in 16..31 &&
        updData[3] in 16..31 &&
        updData[5] in 16..31 &&
        updData[7] in 16..31 -> {

            dchCurr = DataChunkCurrent(
                onesAndTens(byteToInt(updData[0]).toUInt() , byteToInt(updData[1]).toUInt()-16u),
                onesAndTens(byteToInt(updData[2]).toUInt() , byteToInt(updData[3]).toUInt()-16u),
                onesAndTens(byteToInt(updData[4]).toUInt() , byteToInt(updData[5]).toUInt()-16u),
                onesAndTens(byteToInt(updData[6]).toUInt() , byteToInt(updData[7]).toUInt()-16u),

                onesAndTens(byteToInt( updData[8]).toUInt() , byteToInt(updData[9] ).toUInt()-16u),
                onesAndTens(byteToInt(updData[10]).toUInt(),  byteToInt(updData[11]).toUInt()-16u),
                onesAndTens(byteToInt(updData[12]).toUInt(),  byteToInt(updData[13]).toUInt()-16u),
                onesAndTens(byteToInt(updData[14]).toUInt(),  byteToInt(updData[15]).toUInt()-16u)
            )
            //println("CURR  ${updData.joinToString()}||${dchCurr.toString()}")
            CoroutineScope(crtx1).launch {
                dataChunkCurrents.emit(dchCurr)
                //firstGaugeData  .emit(dch.firstGaugeData)
            }
            if (DEBUG_PARSING) {
                arrCurrRaw.add(updData)

                arrCurr.add(arrayListOf(
                    dchCurr.firstCurrentData,
                    dchCurr.secondCurrentData,
                    dchCurr.thirdCurrentData,
                    dchCurr.fourthCurrentData,
                    dchCurr.fifthCurrentData,
                    dchCurr.sixthCurrentData,
                    dchCurr.seventhCurrentData,
                    dchCurr.eighthCurrentData,
                ))
            }


        }
        //pressure
        updData[1] < 16 && updData[3] < 16 && updData[5] < 16 && updData[7] < 16 -> {
            //println("> ${updData.toHexString()} [size:${updData.size}]")

            dch = DataChunkG(
                onesAndTens(byteToInt(updData[0]).toUInt() , byteToInt(updData[1]).toUInt()),
                onesAndTens(byteToInt(updData[2]).toUInt() , byteToInt(updData[3]).toUInt()),
                onesAndTens(byteToInt(updData[4]).toUInt() , byteToInt(updData[5]).toUInt()),
                onesAndTens(byteToInt(updData[6]).toUInt() , byteToInt(updData[7]).toUInt()),

                onesAndTens(byteToInt( updData[8]).toUInt() , byteToInt(updData[9] ).toUInt()),
                onesAndTens(byteToInt(updData[10]).toUInt(),  byteToInt(updData[11]).toUInt()),
                onesAndTens(byteToInt(updData[12]).toUInt(),  byteToInt(updData[13]).toUInt()),
                onesAndTens(byteToInt(updData[14]).toUInt(),  byteToInt(updData[15]).toUInt())
            )

            //logGarbage(">>> ${dch.toString()}")
            //println("PRES ${dch.toString()}")

            CoroutineScope(crtx1).launch {
                dataChunkGauges.emit(dch)
                //firstGaugeData  .emit(dch.firstGaugeData)
            }

            if (DEBUG_PARSING) {
                arrPressRaw.add(updData)

                arrPress.add(arrayListOf(
                    dch.firstGaugeData,
                    dch.secondGaugeData,
                    dch.thirdGaugeData,
                    dch.fourthGaugeData,
                    dch.fifthGaugeData,
                    dch.sixthGaugeData,
                    dch.seventhGaugeData,
                    dch.eighthGaugeData
                ))
            }
        }
        else -> {
            // if not valid numbers - refresh connection
            initSerialCommunication()
        }

    }

    if (DEBUG_PARSING) {
        // print clear results:
        if (arrPressRaw.size > 9) {
            stopSerialCommunication()
            println("_______current:")
            repeat(arrCurrRaw.size) {
                println(arrCurrRaw[it].toHexString())
            }
            println("_______pressure:")
            repeat(arrPressRaw.size) {
                println(arrPressRaw[it].toHexString())
            }
            ///
            println("********************************")

            repeat(arrCurr.size) {
                println(arrCurr[it])
            }
            repeat(arrPress.size) {
                println(arrPress[it])
            }
        }
    }
}

fun isPacketCompletable() : Boolean {
    if (prs4 > 4 || cur1 > 1) {
        println("BROKEN PACKET ")
        return false
    }else {
        return true
    }

}