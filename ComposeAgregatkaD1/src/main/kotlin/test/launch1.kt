package test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import serial_port.coreParse

fun test1() {
    CoroutineScope(Dispatchers.IO).launch {
        coreParse(byteArrayOf(0xFF.toByte(),0x00,0x00,0x00,0x00))
    }

}