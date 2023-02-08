package utils

import com.fazecast.jSerialComm.SerialPort

fun getComPorts_JustString() : String{
    var comports = SerialPort.getCommPorts()
    var output_comport = ""

    for (i in comports) {
        output_comport+= " ${i.systemPortName},"

    }
    if (comports.isNotEmpty()) {
        COM_PORT = comports[0].systemPortName
    }
    return output_comport
}

fun getComPorts_Array() = SerialPort.getCommPorts().toMutableList()