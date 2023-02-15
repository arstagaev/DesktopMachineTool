package serial_port

import parsing_excel.targetParseScenario
import java.io.File

suspend fun comparatorForLaunchScenario(selectedFile: File) {
    targetParseScenario(selectedFile)
}