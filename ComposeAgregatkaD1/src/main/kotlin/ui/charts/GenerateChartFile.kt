package ui.charts

import showMeSnackBar
import utils.Dir2Reports
import utils.generateTimestampLastUpdate
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

data class Pointer(val x: Float, val y: Float)

fun generateToChartFile(
    arr1: ArrayList<Pointer>,
    arr2: ArrayList<Pointer>? = null,
    arr3: ArrayList<Pointer>? = null,
) {
    val fl = File(Dir2Reports,"\\${generateTimestampLastUpdate()}.txt")
    fl.createNewFile()
    val bw = fl.bufferedWriter()

    try {
        // read lines in txt by Bufferreader
        repeat(arr1.size) {
            bw.write("${arr1[it].x};${arr1[it].y}}\n")
        }
        bw.close()
    }catch (e: Exception){
        showMeSnackBar("Error! ${e.message}")
    }

}

fun getChartFromFile(file: File): ArrayList<Pointer> {
    if (!file.exists() || !file.isFile) {
        return arrayListOf()
    }

    var parsedArray = arrayListOf<Pointer>()
    try {
        // read lines in txt by Bufferreader

        val br = BufferedReader(FileReader(file))
        var line: String?

        while (br.readLine().also { line = it } != null) {
            if (line != "" || line != " ") {
                val items = line?.split(";")?.toTypedArray()

                if (items != null) {

                    parsedArray.add(Pointer(x = items[0].toFloat(),y = items[1].toFloat()))

                }
            }
        }

        br.close()
    } catch (e: Exception) {
        showMeSnackBar("Error! ${e.message}")
    }
    return parsedArray
}
