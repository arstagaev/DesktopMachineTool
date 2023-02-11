package serial_port

import com.fazecast.jSerialComm.SerialPort
import kotlinx.coroutines.*
import utils.COM_PORT
import utils.getComPorts_Array
import utils.toHexString

var serialPort: SerialPort = SerialPort.getCommPort(COM_PORT)
private val crtx2 = CoroutineName("main")

fun initSerialCommunication() {
    repeat(
        getComPorts_Array().size
    ) {
        println(">>>Available Com ports:${getComPorts_Array().get(it).systemPortName} is Open: ${getComPorts_Array().get(it).isOpen}||${getComPorts_Array().get(it).descriptivePortName}")
    }

    CoroutineScope(Dispatchers.IO).launch {

        launchSerialCommunication()

        delay(3000)
        println("Run Callbacks::")

        parseBytesCallback()
        writeToSerialPort(byteArrayOf(0x74.toByte(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))

    }
}

fun launchSerialCommunication() {
    println(">>>serial communication has been started, COM_PORT:$COM_PORT")
    serialPort = SerialPort.getCommPort(COM_PORT)
    //SerialPort.getCommPorts()


    //serialPort.baudRate = speedOfPort.value.text.toInt()

    serialPort.setComPortParameters(512000,8,1, SerialPort.NO_PARITY)
    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0)
    serialPort.openPort()
    serialPort.clearBreak()
    //showMeSnackBar("baudRate of Port:${speedOfPort.value.text.toInt()} ", Color.White)
}

suspend fun writeToSerialPort(sendBytes: ByteArray, withFlush: Boolean = false, delay: Long = 1000L) {
    repeat(1) {

        println("Run Send bytes:: ${sendBytes.toHexString()}   size of bytes: ${sendBytes.size}")
        serialPort.writeBytes(sendBytes, sendBytes.size.toLong())
        if (withFlush) {
            serialPort.flushIOBuffers()
        }
        delay(delay)
        //println("goo " + sendBytes.size)
    }

}

fun stopSerialCommunication() {
    serialPort.removeDataListener()
    serialPort.closePort()

    println(">< STOP SERIAL PORT // is Open:${serialPort.isOpen}")
}