package ui.parts_of_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

val textStateMin = mutableStateOf(TextFieldValue("0"))
val textStateMax = mutableStateOf(TextFieldValue("4096"))

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun leftPiece(){
    val openDialog = remember { mutableStateOf(false)  }
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
                    modifier = Modifier.width(300.dp).height(300.dp).background(Color.Gray),
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
    Column (
        modifier = Modifier //.padding(10.dp)
            .width(200.dp)
            .height(1000.dp)
            .background(Color.White)

    ) {
        Text("Agregatka",
            modifier = Modifier.padding(4.dp), fontFamily = FontFamily.Monospace)

        Button(

            onClick = { openDialog.value = true },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray,contentColor = Color.Black),
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Set up Min & Max")
        }
        Button(

            onClick = { /* Do something! */ },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray, contentColor = Color.Black),
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Set up locations of setting file")
        }
        Button(

            onClick = { /* Do something! */ },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray,contentColor = Color.Black),
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Scenarios")
        }

    }
}