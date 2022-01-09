package utils

import com.fazecast.jSerialComm.SerialPort

fun getCommaports() : String{
    var comports = SerialPort.getCommPorts()
    var output_comports = ""

    for (i in comports) {
        output_comports+= " ${i.systemPortName},"

    }
    if (comports.isNotEmpty()) {
        COM_PORT = comports[0].systemPortName
    }
    return output_comports
}