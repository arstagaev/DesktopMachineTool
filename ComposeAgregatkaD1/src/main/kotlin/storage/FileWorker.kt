package storage

import enums.StateExperiments
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import showMeSnackBar
import storage.models.ParamComm
import storage.models.ParameterCommon
import utils.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun checkNeededFolders(): Boolean {
        return false
}

fun createNeededFolders() {
    logAct("createNeededFolders")

  var dirs = arrayOf<File>()
  dirs+= Dir1Configs
  dirs+= Dir2Reports
  dirs+= Dir3Scenarios

  dirs.forEach {
          if (!it.exists()) {
                  it.mkdirs()
                  logAct("Folder created: ${it.absoluteFile}")
          }
  }
}

fun createDemoConfigFile() : File {
    logAct("createDemoConfigFile")
   val theFileXls = File(Dir3Scenarios,"scenario_demo.xls")
   if (!theFileXls.exists()) {
           theFileXls.createNewFile()
           logAct("Excel file-config created: ${theFileXls.absoluteFile}")
   }
    return theFileXls
}

fun readParameters(file: File) : List<ParameterCommon> {
    logAct("readParameters")
    if (!file.exists()) {
        createParameters()
    }

    val PCListSerializer: KSerializer<List<ParameterCommon>> = ListSerializer(ParameterCommon.serializer())

    val obj = Json.decodeFromString(PCListSerializer, file.readText(Charsets.UTF_8))

    //Json.decodeFromString<ArrayList<ParameterCommon>>()


    return obj
}

fun createParameters() {
    logAct("createParameters")
    //"comport" -> COM_P
    //"baudrate" -> BAUD
    ////"is_demo" ->
    //"last_operator_id"
    var newParameters = arrayListOf<ParameterCommon>(
        ParameterCommon("comport","COM10"),
        ParameterCommon("baudrate","COM10"),
        ParameterCommon("last_operator_id","Жималбек Аббас Гамлядиндов Оглы"),
    )


    //files:
//    val fl = Dir4MainConfig_Txt
//    fl.createNewFile()
//    val bw = fl.bufferedWriter()
//
//    try {
//        // read lines in txt by Bufferreader
//        repeat(newParameters.size) {
//            bw.write("${newParameters[it].name}=${newParameters[it].value}\n")
//        }
//        bw.close()
//    }catch (e: Exception){
//        showMeSnackBar("Error! ${e.message}")
//    }

    val PCListSerializer: KSerializer<List<ParameterCommon>> = ListSerializer(ParameterCommon.serializer())

    // json:
    val json = Json.encodeToString(PCListSerializer, newParameters)

    var newFileJson = Dir4MainConfig_Json
    newFileJson.writeText(json)

    if (!newFileJson.exists()) {
        newFileJson.createNewFile()
    }

}

fun refreshParametersJson() {
    logAct("refreshParametersJson -> ${COM_PORT}  ${BAUD_RATE}")
    val json = Json.encodeToString(arrayListOf<ParameterCommon>(
        ParameterCommon("comport", COM_PORT),
        ParameterCommon("baudrate", BAUD_RATE.toString()),
        ParameterCommon("last_operator_id", OPERATOR_ID),
    )
    )

    var newFileJson = Dir4MainConfig_Json
    newFileJson.writeText(json)

    newFileJson.createNewFile()
}

fun loadOperators() : MutableList<String> {
    return Dir5Operators.readLines().toMutableList().asReversed()
}

fun createMeasureExperiment() {
    if (arr1Measure.isEmpty())
        return

    val fl = File(Dir2Reports, generateTimestampLastUpdate()+"_${OPERATOR_ID}"+"_chart.txt")
    CoroutineScope(Dispatchers.Default).launch {
        logInfo("createMeasureExperiment ${arr8Measure.joinToString()}")


        fl.createNewFile()
        val bw = fl.bufferedWriter()
        try {
            // read lines in txt by Bufferreader

            repeat(arr1Measure.size-1) {
                val newStroke =
                    "${arr1Measure[it].x};${arr1Measure[it].y}|"+
                    "${arr2Measure[it].x};${arr2Measure[it].y}|"+
                    "${arr3Measure[it].x};${arr3Measure[it].y}|"+
                    "${arr4Measure[it].x};${arr4Measure[it].y}|"+
                    "${arr5Measure[it].x};${arr5Measure[it].y}|"+
                    "${arr6Measure[it].x};${arr6Measure[it].y}|"+
                    "${arr7Measure[it].x};${arr7Measure[it].y}|"+
                    "${arr8Measure[it].x};${arr8Measure[it].y}"
                logInfo("newStroke= ${newStroke}")
                bw.write("${newStroke}\n")
            }
            bw.close()

            arr1Measure.clear()
            arr2Measure.clear()
            arr3Measure.clear()
            arr4Measure.clear()
            arr5Measure.clear()
            arr6Measure.clear()
            arr7Measure.clear()
            arr8Measure.clear()

        }catch (e: Exception){
            showMeSnackBar("Error! ${e.message}")
        }
    }
    chartFileAfterExperiment.value = fl
    doOpen_First_ChartWindow.value = true
}

fun readMeasuredExperiment(file: File) {
    try {
        val br = BufferedReader(FileReader(file))
        var line: String?
        var countOfLine = 0
        while (br.readLine().also { line = it } != null) {
            if(line != ""|| line != " "){
                val items = line?.split(";","|")?.toTypedArray()
                if (items != null ) {

                }
            }
            countOfLine++
        }
        br.close()
    } catch (e: Exception) {
        logError("error +${e.message}")
    }
}
