package ui.parts_of_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import enums.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import serial_port.initSerialCommunication
import serial_port.serialPort
import serial_port.stopSerialCommunication
import serial_port.writeToSerialPort
import showMeSnackBar
import utils.COM_PORT
import utils.DELAY_FOR_GET_DATA
import utils.GLOBAL_STATE
import utils.getComPorts_JustString
import visiMainScr

var textStateMin = mutableStateOf(TextFieldValue("-1"))
var textStateMax = mutableStateOf(TextFieldValue("4096"))
var textCOMPORT= mutableStateOf(TextFieldValue("COM10"))
var textDelay = mutableStateOf(TextFieldValue("200"))
var speedOfPort = mutableStateOf(TextFieldValue("512000"))
var timeOfMeasure = mutableStateOf(0L)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun leftPiece(visibilityOfMainScreen: Boolean) {

    var openDialog = remember { mutableStateOf(false)  }
    val openDialogSettings = remember { mutableStateOf(false)  }
    val rmbcrtx = rememberCoroutineScope().coroutineContext
    if (openDialog.value) {

        AlertDialog(
            backgroundColor = Color.Black,
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {

            },
            text = {
                Column (
                    modifier = Modifier.width(200.dp).height(300.dp).background(Color.Gray),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "Set Min & Min value", color = Color.Green)
                    TextField(
                        value = textStateMin.value,
                        onValueChange = {
                            //it.text.
                            if (it.text.toIntOrNull() != null) {
                                textStateMin.value = it
                            }

                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    TextField(
                        value = textStateMax.value,
                        onValueChange = {

                            if (it.text.toIntOrNull() != null) {
                                textStateMax.value = it
                            }

                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                }
            },
            confirmButton = {
//                Button(
//
//                    onClick = {
//                        openDialog.value = false
//                    }) {
//                    Text("This is the Confirm Button")
//                }
            },
            dismissButton = {
                Button(

                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Save")
                }
            }
        )
    }
    if (openDialogSettings.value == true) {

        AlertDialog(
            backgroundColor = Color.Black,
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(text = "Main Setup", color = Color.Green) },
            text = {
                Column (
                    modifier = Modifier.width(300.dp).height(300.dp).background(Color.Gray),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "Выберите ComPort, например: COM3", color = Color.Green)
                    TextField(
                        value = textCOMPORT.value,
                        onValueChange = {
                            //it.text.
                            if (it.text != null) {
                                textCOMPORT.value = it
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                    )
                    Text(text = "Задержка для стрелки", color = Color.Green)
                    TextField(
                        value = textDelay.value,
                        onValueChange = {
                            //it.text.
                            if (it.text.toIntOrNull() != null) {
                                textDelay.value = it
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Text(text = "BoundRate ComPort", color = Color.Green)
                    TextField(
                        value = speedOfPort.value,
                        onValueChange = {
                            if (it.text.toIntOrNull() != null) {
                                speedOfPort.value = it
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(

                    onClick = {

                        CoroutineScope(rmbcrtx).launch {
                            //writeToSerialPort(byteArrayOf(0x78.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00, 0x00, 0x00),withFlush = false)
                            writeToSerialPort(byteArrayOf(0x74.toByte(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
                            delay(500)
                            //initSerialCommunication()
                        }

                        openDialogSettings.value = false
                        GLOBAL_STATE.value = State.PLAY
                    }) {
                    Text("Start")
                }
            },
            dismissButton = {
                Button(

                    onClick = {
                        //stopSerialCommunication()
                        openDialogSettings.value = false
                    }) {
                    Text("Cancel")
                }
            }
        )
    }
    Column (
        modifier = Modifier //.padding(10.dp)
            .width(200.dp)
            .height(1000.dp)
            .background(Color.White)

    ) {
        Text("Порт: ${textCOMPORT.value.text}",
            modifier = Modifier.width(200.dp).height(30.dp).padding(4.dp),fontSize = 20.sp, fontFamily = FontFamily.Monospace, )
        Text("Boundrate: ${speedOfPort.value.text}",
            modifier = Modifier.width(200.dp).height(30.dp).padding(4.dp),fontSize = 14.sp, fontFamily = FontFamily.Monospace, )

        Text("Доступные порты:${getComPorts_JustString()}",
            modifier = Modifier.width(200.dp).height(40.dp).padding(4.dp), fontFamily = FontFamily.Monospace, fontSize = 15.sp)
//        Text("Port is open:${getCommaports()}",
//            modifier = Modifier.width(200.dp).height(40.dp).padding(4.dp), fontFamily = FontFamily.Monospace, fontSize = 15.sp)

        Row(modifier = Modifier.width(IntrinsicSize.Min).height(90.dp)){
            Button(
                onClick = { openDialogSettings.value = true },
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Gray,contentColor = Color.Black),
                modifier = Modifier.weight(1f)
            ) {
                Text("▶", modifier = Modifier.size(20.dp))
            }
            Button(

                onClick = {
                    stopSerialCommunication()
                    GLOBAL_STATE.value = State.STOP
                },

                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Gray,contentColor = Color.Black),
                modifier = Modifier.weight(1f)
            ) {
                Text("✖", modifier = Modifier.size(20.dp))
            }


            Button(

                onClick = {
                    CoroutineScope(rmbcrtx).launch {
                        //writeToSerialPort(byteArrayOf(0x54.toByte(), 0x8A.toByte(), 0x02.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00, 0x00, 0x00),withFlush = false)
                        writeToSerialPort(byteArrayOf(0x54.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00, 0x00, 0x00),withFlush = false)
                        GLOBAL_STATE.value = State.STOP
                    }

                },

                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Gray,contentColor = Color.Black),
                modifier = Modifier.weight(1f)
            ) {
                Text("⏸", modifier = Modifier.size(20.dp))
            }

            Button(

                onClick = {
                    CoroutineScope(rmbcrtx).launch {
                        writeToSerialPort(byteArrayOf(0x78.toByte(), 0x8A.toByte(), 0x02.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00, 0x00, 0x00),withFlush = false)
                        delay(500)
                        serialPort.flushIOBuffers()
                        writeToSerialPort(byteArrayOf(0x54.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00, 0x00, 0x00),withFlush = false)
                        //writeToSerialPort(byteArrayOf(0x54.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00, 0x00, 0x00),withFlush = false)

                        GLOBAL_STATE.value = State.STOP
                    }

                },

                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Gray,contentColor = Color.Black),
                modifier = Modifier.weight(1f)
            ) {
                Text("⟲", modifier = Modifier.size(20.dp))
            }
            //initSerialCommunication("COM3")
        }
        Text("Time: ${(timeOfMeasure.value/1000L).toInt()}s",
            modifier = Modifier.width(200.dp).height(60.dp).padding(4.dp),fontSize = 14.sp, fontFamily = FontFamily.Monospace, )

        Button(

            onClick = { openDialog.value = true },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray,contentColor = Color.Black),
            modifier = Modifier.padding(5.dp)
        ) {
            Text("Установить Min и Max")
        }
        Button(

            onClick = { /* Do something! */ },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray, contentColor = Color.Black),
            modifier = Modifier.padding(5.dp)
        ) {
            Text("Set up locations of setting file")
        }
        Button(

            onClick = { /* Do something! */ },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray,contentColor = Color.Black),
            modifier = Modifier.padding(5.dp)
        ) {
            Text("Scenarios")
        }
        Button(
            onClick = {
                visiMainScr.value = !visiMainScr.value
                if (visiMainScr.value) {
                    showMeSnackBar("Это графики",Color.White)
                }
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray,contentColor = Color.Black),
            modifier = Modifier.padding(5.dp)
        ) {
            Text("Chart")
        }

    }
}