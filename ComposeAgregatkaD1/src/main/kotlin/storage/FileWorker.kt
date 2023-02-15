package storage

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import storage.models.ParameterCommon
import utils.*
import java.io.File
import javax.swing.JFileChooser

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
   val theFileXls = File("${Dir1Configs.absolutePath}\\config_demo.xls")
   if (!theFileXls.exists()) {
           theFileXls.createNewFile()
           logAct("Excel file-config created: ${theFileXls.absoluteFile}")
   }
    return theFileXls
}

fun readParameters(file: File) : ArrayList<ParameterCommon> {
    logAct("readParameters")
    if (!file.exists()) {
        createParameters()
    }

    val obj = Json.decodeFromString<ArrayList<ParameterCommon>>(file.readText(Charsets.UTF_8))


    return obj
}

fun createParameters() {
    logAct("createParameters")
    //"comport" -> COM_P
    //"baudrate" -> BAUD
    ////"is_demo" ->
    //"last_operator_id"
    val json = Json.encodeToString(arrayListOf<ParameterCommon>(
        ParameterCommon("comport","COM10"),
        ParameterCommon("baudrate","COM10"),
        ParameterCommon("last_operator_id","Жималбек Аббас Гамлядиндов Оглы"),
        )
    )
    var newFileJson = Dir4MainConfig
    newFileJson.writeText(json)
    if (!newFileJson.exists()) {
        newFileJson.createNewFile()
    }
}

fun refreshParametersJson() {
    logAct("refreshParametersJson")
    val json = Json.encodeToString(arrayListOf<ParameterCommon>(
        ParameterCommon("comport", COM_PORT),
        ParameterCommon("baudrate", BAUD_RATE.toString()),
        ParameterCommon("last_operator_id", OPERATOR_ID),
    )
    )

    var newFileJson = Dir4MainConfig
    newFileJson.writeText(json)

    newFileJson.createNewFile()
}

fun loadOperators() : MutableList<String> {
    return Dir5Operators.readLines().toMutableList().asReversed()
}