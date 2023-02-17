package serial_port

import com.fazecast.jSerialComm.SerialPort
import kotlinx.coroutines.*
import startTimer
import utils.COM_PORT
import utils.arrayOfComPorts
import utils.getComPorts_Array
import utils.toHexString

private var serialPort: SerialPort = SerialPort.getCommPort(COM_PORT)
private val crtx2 = CoroutineName("main")

suspend fun initSerialCommunication() {
    println(">>>serial communication has been started, COM_PORT:$COM_PORT")
    serialPort = SerialPort.getCommPort(COM_PORT)
    serialPort.setComPortParameters(500000,8,1, SerialPort.NO_PARITY)
    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)
    serialPort.openPort()
    //serialPort.clearBreak()
    arrayOfComPorts = getComPorts_Array() as Array<SerialPort>

    delay(2000)
    println("Run Callbacks::")
    val listener = PacketListener()
    serialPort.addDataListener(listener)
    //showMeSnackBar("baudRate of Port:${speedOfPort.value.text.toInt()} ", Color.White)
}

suspend fun startReceiveFullData() {
//    repeat(
//        getComPorts_Array().size
//    ) {
//        println(">>>Available Com ports:${getComPorts_Array().get(it).systemPortName} is Open: ${getComPorts_Array().get(it).isOpen}||${getComPorts_Array().get(it).descriptivePortName}")
//    }

    writeToSerialPort(byteArrayOf(0x74.toByte(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00))

}

fun stopSerialCommunication() {
    serialPort.removeDataListener()
    serialPort.closePort()

    println(">< STOP SERIAL PORT // is Open:${serialPort.isOpen}")
}

suspend fun pauseSerialComm() {
    writeToSerialPort(byteArrayOf(0x71,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00),false, delay = 0L)
    writeToSerialPort(byteArrayOf(0x51,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00),false, delay = 0L)

    writeToSerialPort(byteArrayOf(0x78, 0x8A.toByte(), 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00),withFlush = false)
    delay(500)
    //serialPort.flushIOBuffers()
    writeToSerialPort(byteArrayOf(0x54, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00,0x00, 0x00,0x00),withFlush = false)

}

suspend fun writeToSerialPort(sendBytes: ByteArray, withFlush: Boolean = false, delay: Long = 1000L) {
//    if (sendBytes[0] == 0x74.toByte()) {
//        //startTimer()
//    }
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

