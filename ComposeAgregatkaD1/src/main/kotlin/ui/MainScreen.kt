import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import kotlinx.coroutines.flow.MutableStateFlow
import ui.parts_of_screen.centerPiece
import ui.parts_of_screen.leftPiece
import ui.parts_of_screen.rightPiece
import kotlin.concurrent.fixedRateTimer
//import VariablesUSB.*

//val serialPort: SerialPort = SerialPort.getCommPort("COM3")


@Composable
@Preview
fun App() {
    var PRESSURE by remember { mutableStateOf(0) }


    fixedRateTimer("timer", false, 5000L,2000) {
        PRESSURE = (0..90).random()
        print("well ${PRESSURE}")
    }



    MaterialTheme {
        Row{
            leftPiece()
            centerPiece(PRESSURE,(PRESSURE*0.4).toInt(),1,1,11,23,1,1)
            rightPiece()
        }


    }
}

//
//private fun initSerialCommunication() {
//    println(">>>serial communication has been started")
//    serialPort.baudRate = 115200
//    serialPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY)
//    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0)
//    serialPort.openPort()
//
//    var a = 0
//    val bb = byteArrayOf(0x74.toByte(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
//    try {
//        Thread.sleep(2000)
//    } catch (e: InterruptedException) {
//        e.printStackTrace()
//    }
//    while (a < 10) {
//        serialPort.writeBytes(bb, 1)
//        //            try {
//        println("goo " + bb.size)
//        //                serialPort.getOutputStream().write(bb);
////                //serialPort.getOutputStream().flush();
////            } catch (IOException e) {
////                System.out.println("pizdec "+e.getMessage());
////                e.printStackTrace();
////            }
//        a++
//    }
//
//    serialPort.addDataListener(object : SerialPortDataListener {
//        override fun getListeningEvents(): Int {
//            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE
//        }
//
//        override fun serialEvent(event: SerialPortEvent) {
//            if (event.eventType != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
//                sound(-1)
//                return
//            }
//            newData = ByteArray(serialPort.bytesAvailable())
//            val numRead = serialPort.readBytes(newData, newData.size.toLong())
//            //var nnn = uintArrayOf(newData)
//            //nnn =
//
//            println("conv " + (newData.toUByteArray()).joinToString() + "[[ ${newData.size}")
//            //curPoint =
//            firstAnalog( filterKalman( onesAndTens(newData[0],newData[1])))
//            secondAnalog(onesAndTens(newData[2],newData[3]))
//            thirdAnalog( onesAndTens(newData[4],newData[5]))
//            fourthAnalog(onesAndTens(newData[6],newData[7]))
//
//            fiveAnalog( onesAndTens(newData[8],newData[9]))
//            sixAnalog(  onesAndTens(newData[10],newData[11]))
//            sevenAnalog(onesAndTens(newData[12],newData[13]))
//            eightAnalog(onesAndTens(newData[14],newData[15]))
//            z++
//        }
//    })
//}
//
//fun stopSerialCommunication(){
//    serialPort.removeDataListener()
//    serialPort.closePort()
//}