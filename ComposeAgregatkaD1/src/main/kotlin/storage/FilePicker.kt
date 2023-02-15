package storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import parsing_excel.targetParseScenario
import serial_port.comparatorForLaunchScenario
import utils.Dir3Scenarios
import java.io.File
import javax.swing.JFileChooser

import javax.swing.filechooser.FileNameExtensionFilter


//https://mkyong.com/swing/java-swing-jfilechooser-example/

fun openPicker(targetDir: File) {
    val chooser = JFileChooser(targetDir)
    val filter = FileNameExtensionFilter(
        "Config", "xls","xlsx"
    )
    chooser.fileFilter = filter
    val returnVal = chooser.showOpenDialog(null)
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        println(
            "You chose to open this file: " +
                    chooser.selectedFile.name
        )
        CoroutineScope(Dispatchers.IO).launch {
            comparatorForLaunchScenario(chooser.selectedFile)
            targetParseScenario(chooser.selectedFile)
        }


    }
}