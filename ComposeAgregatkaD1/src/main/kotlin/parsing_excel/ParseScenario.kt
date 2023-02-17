package parsing_excel

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row
import parsing_excel.models.PressuresHolder
import parsing_excel.models.ScenarioStep
import parsing_excel.models.SolenoidHolder
import utils.limitTime
import utils.pressures
import utils.scenario
import utils.solenoids
import java.io.File
import java.io.FileInputStream

var wholeSheet = mutableListOf<MutableList<String>>()
suspend fun targetParseScenario(inputScenario: File?) : Boolean {

    if (inputScenario == null)
        return false

    val file = FileInputStream(inputScenario)
    //creating workbook instance that refers to .xls file
    val wb = HSSFWorkbook(file)
    //creating a Sheet object to retrieve the object
    val sheet = wb.getSheetAt(0)
    //evaluating cell type
    val formulaEvaluator: FormulaEvaluator = wb.creationHelper.createFormulaEvaluator()

    //Iterate through each row's one by one
    val rowIterator: Iterator<Row> = sheet.iterator()
    var incr = 0

    while (rowIterator.hasNext()) {
        // to bottom V
        val row: Row = rowIterator.next()

        var rowComplete = mutableListOf<String>()
        val cellIterator: Iterator<Cell> = row.cellIterator()

        while (cellIterator.hasNext()) {
            // to right ->
            val cell = cellIterator.next()
            if (cell.toString().isNotBlank() && cell.toString().isNotEmpty()) {
                rowComplete.add(cell.toString())
            }

//            when (cell.cellType) {
//                CellType.NUMERIC -> print(cell.numericCellValue.toString() + "t")
//                CellType.STRING -> print(cell.stringCellValue + "t")
//            }
        }
        println("${incr}Row: ${rowComplete.joinToString()}")
        incr++
        wholeSheet.add(rowComplete)
    }
    println("Size sheet rows:${wholeSheet[2].size} in rows column:${wholeSheet[2][2].length}")

    // check valid of xls:
    if ( wholeSheet.size < 22 || wholeSheet[2].size < 2)
        return false

    // clear all in iteration:
    solenoids.clear()
    pressures.clear()
    scenario.clear()

    repeat(8) {
//        var asd = arrayListOf<String>(
//            wholeSheet[2][it+1],
//            wholeSheet[3][it+1],
//            wholeSheet[4][it+1],
//            wholeSheet[5][it+1],
//            wholeSheet[6][it+1],
//            wholeSheet[7][it+1],
//            wholeSheet[8][it+1],
//            wholeSheet[9][it+1]
//        )
//        println("cooopppppyyy ${it} ${asd.joinToString()} ${pressures.size}")
        //incr++
        pressures.add(
            PressuresHolder(
                displayName =  wholeSheet[2][it+1],
                index =        wholeSheet[3][it+1].toFloat().toInt(),
                minValue =     wholeSheet[4][it+1].toFloat().toInt(),
                maxValue =     wholeSheet[5][it+1].toFloat().toInt(),
                tolerance =    wholeSheet[6][it+1].toFloat().toInt(),
                unit =         wholeSheet[7][it+1],
                commentString =wholeSheet[8][it+1],
                prefferedColor=wholeSheet[9][it+1],
                isVisible = true
            )
        )


    }

    repeat(8) {
//        var asd = arrayListOf<String>(
//            wholeSheet[14][it+1],
//            wholeSheet[15][it+1],
//            wholeSheet[16][it+1],
//            wholeSheet[17][it+1],
//            wholeSheet[18][it+1],
//            wholeSheet[19][it+1],
//            wholeSheet[20][it+1],
//            wholeSheet[21][it+1]
//        )
//        println( pizdec [${it}] ${asd.joinToString()} ${pressures.size}")

        solenoids.add(
            SolenoidHolder(
                displayName =       wholeSheet[14][it+1],
                index =             wholeSheet[15][it+1].toDouble().toInt(),
                maxPWM =            wholeSheet[16][it+1].toDouble().toInt(),
                step =              wholeSheet[17][it+1].toDouble().toInt(),
                preferredColor =    wholeSheet[18][it+1],
                frequency =         wholeSheet[19][it+1].toDouble().toInt(),
                expectedTestValue = wholeSheet[20][it+1].toDouble().toInt(),
                currentMaxValue =   wholeSheet[21][it+1].toDouble().toInt(),
                isVisible = true
            )
        )
    }



    limitTime = 0

    for ( i in (27) until wholeSheet.size) {
        var valueSteps = arrayListOf<Int>()

        repeat(8) {
            valueSteps.add(wholeSheet[i][it+1].toDouble().toInt())

        }

        println("<>>> ${valueSteps.joinToString()}")

        val newTime = wholeSheet[i][0].toDouble().toInt()

        limitTime += newTime

        scenario.add(
            ScenarioStep(
                time = newTime,
                values = valueSteps,
                text = wholeSheet[i][9],
                comment = if (wholeSheet[i].size != 11) "" else wholeSheet[i][10]
            )
        )
    }



    println("scenario steps: ${scenario.joinToString()}")

    file.close()
    wholeSheet.clear()
    return true
}