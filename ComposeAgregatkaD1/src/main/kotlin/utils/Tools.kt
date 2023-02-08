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

fun onesAndTens(onesRAW : UInt, tensRAW : UInt) : Int {
    val ones = onesRAW//.toInt()
    val tens = tensRAW//.toInt()

    //println("${ones.toInt()} ${tens}")

    return if (tens == 0u) {
        //println("ones.toInt()")
        ones.toInt()
    } else {
        //println("( ones + tens * 256u ).toInt()")
        ( ones + tens * 256u ).toInt()
    }
}

fun main() {
    println("${0xFF} ${0xFF.toUInt()}")

    println("> ${onesAndTens((0xFF.toUInt()),0u)}")
    println("> ${onesAndTens(0xFFu,0x0Fu)}")
    println("> ${onesAndTens(0x00u,0x0Fu)}")
    println("> ${onesAndTens(0xFFu,0x01u)}")
}