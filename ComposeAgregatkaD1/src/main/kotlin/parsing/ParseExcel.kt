package parsing

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row
import java.io.File
import java.io.FileInputStream
import javax.swing.JFileChooser



var wholeSheet = mutableListOf<MutableList<String>>()
var solenoids = mutableListOf<SolenoidHolder>()
var pressures = mutableListOf<PressuresHolder>()

fun readExcelFile() {
    val theDir = File("${JFileChooser().fileSystemView.defaultDirectory.toString()}\\agregatka_machinetool")
    if (!theDir.exists()) {
        theDir.mkdirs()
    }
    val theDirXls = File("${JFileChooser().fileSystemView.defaultDirectory.toString()}\\agregatka_machinetool\\config.xls")
    if (!theDirXls.exists()) {
        theDirXls.mkdirs()
    }
    //obtaining input bytes from a file
    val file = FileInputStream(File("${JFileChooser().fileSystemView.defaultDirectory.toString()}\\agregatka_machinetool\\config.xls"))


    //println("well >"+JFileChooser().fileSystemView.defaultDirectory.toString())
    //creating workbook instance that refers to .xls file
    val wb = HSSFWorkbook(file)
    //creating a Sheet object to retrieve the object
    val sheet = wb.getSheetAt(0)
    //evaluating cell type
    val formulaEvaluator: FormulaEvaluator = wb.creationHelper.createFormulaEvaluator()

    //Iterate through each rows one by one

    val rowIterator: Iterator<Row> = sheet.iterator()

    while (rowIterator.hasNext()) {

        val row: Row = rowIterator.next()



        var rowComplete = mutableListOf<String>()
        //println(">>> ${row.getCell(0)} || ${row.getCell(1)}")
        //For each row, iterate through all the columns
        val cellIterator: Iterator<Cell> = row.cellIterator()
        while (cellIterator.hasNext()) {
            val cell = cellIterator.next()
            rowComplete.add(cell.toString())

//            when (cell.cellType) {
//                CellType.NUMERIC -> print(cell.numericCellValue.toString() + "t")
//                CellType.STRING -> print(cell.stringCellValue + "t")
//            }
        }
        println("Row: ${rowComplete.joinToString()}")
        wholeSheet.add(rowComplete)
        //println("")
    }
    file.close()

    for(i in wholeSheet) {
        println("~~~ ${wholeSheet[2][1]} ${wholeSheet[2][2]} ${wholeSheet[2][3]}")
        //i[2][1]
    }

    for (i in 0 until wholeSheet[2].size-1) {
        solenoids.add(
            SolenoidHolder(
                displayName = wholeSheet[2][i+1],
                wholeSheet[3][i+1].toDouble().toInt(),
                wholeSheet[3][i+1].toDouble().toInt(),
                wholeSheet[4][i+1].toDouble().toInt(),
                wholeSheet[5][i+1].toDouble().toInt(),
                wholeSheet[6][i+1],
                wholeSheet[7][i+1].toDouble().toInt(),
                wholeSheet[8][i+1].toBoolean()
            )
        )
    }
    println("><><><>< ${solenoids.joinToString()}")
    println("${wholeSheet[14][1]}  ][ ${ wholeSheet[15][1]}")
    for (i in 0 until wholeSheet[14].size-1) {
        pressures.add(
            PressuresHolder(
                displayName =  wholeSheet[14][i+1],
                index =        wholeSheet[15][i+1].toDouble().toInt(),
                maxValue =     wholeSheet[16][i+1].toDouble().toInt(),
                tolerance =    wholeSheet[17][i+1].toDouble().toInt(),
                unit =         wholeSheet[18][i+1],
                commentString =wholeSheet[19][i+1],
                prefferedColor=wholeSheet[20][i+1]
            )
        )
    }

    println("><><><>< ${pressures.joinToString()}")
}

data class SolenoidHolder(
    val displayName : String,
    val index : Int,
    val maxPWM : Int,
    val tolerance : Int,

    val frequency : Int,
    val preferredColor : String,
    val expectedTestValue : Int,
    val IsDC : Boolean
)
data class PressuresHolder(
    val displayName : String,
    val index : Int,
    val maxValue : Int,
    val tolerance : Int,
    val unit : String,
    val commentString : String,
    val prefferedColor : String
)