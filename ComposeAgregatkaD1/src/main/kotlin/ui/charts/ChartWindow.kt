package ui.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import enums.StateExperiments
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYItemRenderer
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import parsing_excel.writeToExcel
import showMeSnackBar
import storage.PickTarget
import storage.openPicker
import utils.*
import java.awt.BasicStroke
import java.io.*
import javax.swing.BoxLayout
import javax.swing.JPanel

class ChartWindowNew(var withStandard: Boolean = false, val isViewerOnly: Boolean = false) {
    val dataset = XYSeriesCollection()

    private val series1 = XYSeries("Давление 1")
    private val series2 = XYSeries("Давление 2")
    private val series3 = XYSeries("Давление 3")
    private val series4 = XYSeries("Давление 4")
    private val series5 = XYSeries("Давление 5")
    private val series6 = XYSeries("Давление 6")
    private val series7 = XYSeries("Давление 7")
    private val series8 = XYSeries("Давление 8")


    private val series9 =  XYSeries("Давление 1"+(" [Стандарт]"))
    private val series10 = XYSeries("Давление 2"+(" [Стандарт]"))
    private val series11 = XYSeries("Давление 3"+(" [Стандарт]"))
    private val series12 = XYSeries("Давление 4"+(" [Стандарт]"))
    private val series13 = XYSeries("Давление 5"+(" [Стандарт]"))
    private val series14 = XYSeries("Давление 6"+(" [Стандарт]"))
    private val series15 = XYSeries("Давление 7"+(" [Стандарт]"))
    private val series16 = XYSeries("Давление 8"+(" [Стандарт]"))

    var chartPanel: JPanel? = null
    var isLoading = mutableStateOf(false)
    val crtx = CoroutineScope(Dispatchers.Default)

    private var renderer: XYItemRenderer = XYLineAndShapeRenderer(true, false)
    private val xAxis = NumberAxis("time (ms)")
    private val yAxis = NumberAxis("Bar")
    private var plot = XYPlot(dataset, xAxis, yAxis, renderer)
    var chart = JFreeChart(
        "Эталон и текущий эксперимент", JFreeChart.DEFAULT_TITLE_FONT,
        plot,true
    )

    private var sizeStandard   = 0
    private var sizeExperiment = 0

    init {
        crtx.launch {
            delay(1000)
            fillUp()
            delay(1000)
            STATE_EXPERIMENT.value = StateExperiments.NONE
        }.invokeOnCompletion {
            chart = JFreeChart(
                "Эталон и текущий эксперимент", JFreeChart.DEFAULT_TITLE_FONT,
                plot,true
            )
            logGarbage(">>>8 ${it?.message}")
        }


        chartPanel = ChartPanel(chart)
    }

    @Composable
    fun chartWindow() {

        Window(
            title = if (isViewerOnly) "Viewer" else "Compare with Standard",
            state = WindowState(size = DpSize(1000.dp, 800.dp)),
            onCloseRequest = {

                CoroutineScope(Dispatchers.Default).launch {
                    if (isViewerOnly) {
                        doOpen_Second_ChartWindow.value = false
                    } else {
                        doOpen_First_ChartWindow.value = false
                    }
                    delay(100)

                    writeToExcel(0,0, chartFileStandard.value.name)

                }
        },
        ) {
            val chrt = remember { STATE_EXPERIMENT }

            if (chrt.value == StateExperiments.NONE) {
                ChartSecond()
            }else {
                Box(Modifier.fillMaxSize().background(Color.Black)) {
                    Text("Loading...",
                        modifier = Modifier.padding(top = (10).dp,start = 20.dp),
                        fontFamily = FontFamily.Default, fontSize = 40.sp,
                        fontWeight = FontWeight.Bold, color = Color.White
                    )
                }
            }

        }
    }


    private suspend fun fillUp() {
        STATE_EXPERIMENT.value = StateExperiments.PREPARE_CHART
        logAct("fillUp chart ${chartFileAfterExperiment.value.name}  ${chartFileStandard.value.name}")

        logGarbage(">>>1")
        //CoroutineScope(Dispatchers.Default).launch {
            //experiment:
            try {
                val br = BufferedReader(FileReader(chartFileAfterExperiment.value))
                var line: String?
                //var countOfLine = 0
                while (br.readLine().also { line = it } != null) {
                    if (line != "" || line != " ") {
                        val items = line?.split(";", "|")?.toTypedArray()
                        //println("exp >>>> ${items?.joinToString()}")
                        if (items != null) {

                            series1.add(items[0].toInt(), items[1].toInt())

                            series2.add(items[2].toInt(), items[3].toInt()).takeIf { items.size > 2 }
                            series3.add(items[4].toInt(), items[5].toInt()).takeIf { items.size > 4 }
                            series4.add(items[6].toInt(), items[7].toInt()).takeIf { items.size > 6 }
                            series5.add(items[8].toInt(), items[9].toInt()).takeIf { items.size > 8 }
                            series6.add(items[10].toInt(), items[11].toInt()).takeIf { items.size > 10 }
                            series7.add(items[12].toInt(), items[13].toInt()).takeIf { items.size > 12 }
                            series8.add(items[14].toInt(), items[15].toInt()).takeIf { items.size > 14 }
                        }
                    }
                    //countOfLine++
                }
                br.close()
            } catch (e: Exception) {
                logError("error +${e.message}")
            }
            logGarbage(">>>2")
            //standard:
            if (withStandard) {
                try {

                    val br = BufferedReader(FileReader(chartFileStandard.value))
                    var line: String?
                    //var countOfLine = 0
                    while (br.readLine().also { line = it } != null) {
                        if (line != "" || line != " ") {
                            val items = line?.split(";", "|")?.toTypedArray()
                            println("withStandard >>>> ${items?.joinToString()}")
                            if (items != null) {
                                series9.add(items[0].toInt(), items[1].toInt())
                                series10.add(items[2].toInt(), items[3].toInt()).takeIf { items.size > 2 }
                                series11.add(items[4].toInt(), items[5].toInt()).takeIf { items.size > 4 }
                                series12.add(items[6].toInt(), items[7].toInt()).takeIf { items.size > 6 }
                                series13.add(items[8].toInt(), items[9].toInt()).takeIf { items.size > 8 }
                                series14.add(items[10].toInt(), items[11].toInt()).takeIf { items.size > 10 }
                                series15.add(items[12].toInt(), items[13].toInt()).takeIf { items.size > 12 }
                                series16.add(items[14].toInt(), items[15].toInt()).takeIf { items.size > 14 }
                            }
                        }
                        //countOfLine++
                    }
                    br.close()
                } catch (e: Exception) {
                    logError("error +${e.message}")
                    showMeSnackBar("Error Chart:  ${e.message}", Color.Red)
                }


            }
        //}

        logGarbage(">>>3")
        sizeExperiment = series1.items.size
            dataset.addSeries(series1)
            dataset.addSeries(series2)
            dataset.addSeries(series3)
            dataset.addSeries(series4)
            dataset.addSeries(series5)
            dataset.addSeries(series6)
            dataset.addSeries(series7)
            dataset.addSeries(series8)

            if (withStandard) {
                sizeStandard = series10.items.size
                //logInfo("chart series16 ${series16.maximumItemCount} ${series16}")
                dataset.addSeries(series9)
                dataset.addSeries(series10)
                dataset.addSeries(series11)
                dataset.addSeries(series12)
                dataset.addSeries(series13)
                dataset.addSeries(series14)
                dataset.addSeries(series15)
                dataset.addSeries(series16)
            }


            xAxis.autoRangeIncludesZero = false

        logGarbage(">>>4")
            val arrClrExperiment = arrayOf(
                java.awt.Color.RED,
                java.awt.Color.ORANGE,
                java.awt.Color.YELLOW,
                java.awt.Color.GREEN,
                java.awt.Color.BLUE,
                java.awt.Color.CYAN,
                java.awt.Color.MAGENTA,
                java.awt.Color.BLACK
            )

            val arrClrStandard = arrayOf(
                java.awt.Color(255, 145, 145),
                java.awt.Color(255, 200, 50),
                java.awt.Color(255, 255, 150),
                java.awt.Color(147, 255, 100),
                java.awt.Color(147, 147, 255),
                java.awt.Color(147, 255, 255),
                java.awt.Color(255, 147, 255),
                java.awt.Color(128, 128, 128)
            )
        logGarbage(">>>5")
            repeat(8) {
                //renderer
                renderer.setSeriesPaint(it, arrClrExperiment[it])
                renderer.setSeriesStroke(it, BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND))
                //renderer.setSeriesShape(it+8,ShapeUtilities.createDiamond(0f))
            }

            if (withStandard) {
                repeat(8) {

                    //renderer.setSeriesShape(it+8,ShapeUtilities.createDiamond(6f))
                    renderer.setSeriesPaint(it + 8, arrClrStandard[it])
                    renderer.setSeriesStroke(
                        it + 8,
                        //ShapeUtilities.createDiamond(6f)
                        BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1f, floatArrayOf(10f), 0.5f)
                    )
                    //renderer.sha
                }
            }


        logGarbage(">>>6")
        plot = XYPlot(dataset, xAxis, yAxis, renderer)
        plot.setOrientation(PlotOrientation.VERTICAL)
        logGarbage(">>>7")
        plot.setRenderer(renderer)
        soundUniversal(Dir0Configs_End)
    }

    @Composable
    fun ChartSecond() {
        val standardFile = remember { chartFileStandard }
        //val loader = remember { isLoading }
        Box {
            Column(
                modifier = Modifier.fillMaxSize()//fillMaxWidth().height(800.dp)
            ) {
                Row(Modifier.fillMaxWidth().height(20.dp).background(Color.Yellow)) {

                    Box(Modifier.fillMaxWidth().height(20.dp).weight(1f).clickable {
                        openPicker(Dir7ReportsStandard, PickTarget.PICK_STANDARD_CHART).let {
                            if(it != null) {
                                chartFileStandard.value = it
                                CoroutineScope(Dispatchers.Default).launch {
                                    writeToExcel(0,0, chartFileStandard.value.name)
                                }

                            }
                        }

                        CoroutineScope(Dispatchers.Default).launch {
                            fillStandard(isRefresh = true)
                        }

                    }){
                        Text("Эталон(${sizeStandard}): ${standardFile.value.name}", modifier = Modifier.padding(0.dp).align(Alignment.Center),
                            fontFamily = FontFamily.Default, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Black
                        )
                    }
                    Box(Modifier.fillMaxWidth().height(20.dp).weight(1f).clickable {
                        openPicker(Dir2Reports, PickTarget.PICK_CHART).let { if(it != null) chartFileAfterExperiment.value = it }

                        CoroutineScope(Dispatchers.Default).launch {
                            fillExperiment(isRefresh = true)
                        }

                    }){
                        Text("Эксперимент(${sizeExperiment}): ${chartFileAfterExperiment.value.name}", modifier = Modifier.padding(0.dp).align(Alignment.Center),
                            fontFamily = FontFamily.Default, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Black
                        )
                    }
                }
                Box(Modifier.fillMaxWidth().height(20.dp).weight(1f).clickable {
                    openPicker(Dir7ReportsStandard, PickTarget.PICK_STANDARD_CHART).let { if(it != null) chartFileStandard.value = it }

                    CoroutineScope(Dispatchers.Default).launch {
                        fillStandard(isRefresh = true)
                    }

                }){
                    Text("Эталон: ${standardFile.value.name}", modifier = Modifier.padding(0.dp).align(Alignment.Center),
                        fontFamily = FontFamily.Default, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.Black
                    )
                }
                SwingPanel(
                    background = Color.DarkGray,
                    modifier = Modifier.fillMaxSize(),//.size(width = 1000.dp, height =  600.dp),
                    factory = {
                        JPanel().apply {
                            setLayout(BoxLayout(this, BoxLayout.Y_AXIS))
                            add(chartPanel)

                        }
                    }
                )
            }
//            if (loader.value) {
//                Box(Modifier.fillMaxSize().background(colorTrans60)){
//                    Text("Загрузка...", modifier = Modifier.padding(0.dp).align(Alignment.Center),
//                        fontFamily = FontFamily.Default, fontSize = 40.sp, fontWeight = FontWeight.Medium, color = Color.Blue
//                    )
//                }
//            }

        }

    }

    private suspend fun fillStandard(isRefresh: Boolean = false) {
        isLoading.value = true
        logAct("fillStandard !!!")
        try {
            series9.clear()
            series10.clear()
            series11.clear()
            series12.clear()
            series13.clear()
            series14.clear()
            series15.clear()
            series16.clear()

            //val series9 =  XYSeries("Давление 1"+(" [Стандарт]"))
            //val series10 = XYSeries("Давление 2"+(" [Стандарт]"))
            //val series11 = XYSeries("Давление 3"+(" [Стандарт]"))
            //val series12 = XYSeries("Давление 4"+(" [Стандарт]"))
            //val series13 = XYSeries("Давление 5"+(" [Стандарт]"))
            //val series14 = XYSeries("Давление 6"+(" [Стандарт]"))
            //val series15 = XYSeries("Давление 7"+(" [Стандарт]"))
            //val series16 = XYSeries("Давление 8"+(" [Стандарт]"))

            val br = BufferedReader(FileReader(chartFileStandard.value))
            var line: String?
            //var countOfLine = 0
            while (br.readLine().also { line = it } != null) {
                if(line != ""|| line != " ") {
                    val items = line?.split(";","|")?.toTypedArray()
                    if (items != null ) {
                        series9 .add(items[0].toInt(),items[1].toInt())
                        series10.add(items[2].toInt(),items[3].toInt()).takeIf { items.size > 2 }
                        series11.add(items[4].toInt(),items[5].toInt()).takeIf { items.size > 4 }
                        series12.add(items[6].toInt(),items[7].toInt()).takeIf { items.size > 6 }
                        series13.add(items[8].toInt(),items[9].toInt()).takeIf { items.size > 8 }
                        series14.add(items[10].toInt(),items[11].toInt()).takeIf { items.size > 10 }
                        series15.add(items[12].toInt(),items[13].toInt()).takeIf { items.size > 12 }
                        series16.add(items[14].toInt(),items[15].toInt()).takeIf { items.size > 14 }
                    }
                }
                //countOfLine++
            }
            br.close()
        } catch (e: Exception) {
            logError("error +${e.message}")
            showMeSnackBar("Error Chart:  ${e.message}",Color.Red)
        }


        if (!isRefresh) {

        }

        //dataset.notify = true
        series9.notify = true
        series10.notify = true
        series11.notify = true
        series12.notify = true
        series13.notify = true
        series14.notify = true
        series15.notify = true
        series16.notify = true

        isLoading.value = false
    }

    private suspend fun fillExperiment(isRefresh: Boolean = false) {
        isLoading.value = true
        logAct("fillExperiment !!!")
        try {
            series1.clear()
            series2.clear()
            series3.clear()
            series4.clear()
            series5.clear()
            series6.clear()
            series7.clear()
            series8.clear()

            //val series9 =  XYSeries("Давление 1"+(" [Стандарт]"))
            //val series10 = XYSeries("Давление 2"+(" [Стандарт]"))
            //val series11 = XYSeries("Давление 3"+(" [Стандарт]"))
            //val series12 = XYSeries("Давление 4"+(" [Стандарт]"))
            //val series13 = XYSeries("Давление 5"+(" [Стандарт]"))
            //val series14 = XYSeries("Давление 6"+(" [Стандарт]"))
            //val series15 = XYSeries("Давление 7"+(" [Стандарт]"))
            //val series16 = XYSeries("Давление 8"+(" [Стандарт]"))

            val br = BufferedReader(FileReader(chartFileAfterExperiment.value))
            var line: String?
            //var countOfLine = 0
            while (br.readLine().also { line = it } != null) {
                if(line != ""|| line != " ") {
                    val items = line?.split(";","|")?.toTypedArray()
                    if (items != null ) {
                        series1.add(items[0].toInt(),items[1].toInt())

                        series2.add(items[2].toInt(),items[3].toInt()).takeIf { items.size > 2 }
                        series3.add(items[4].toInt(),items[5].toInt()).takeIf { items.size > 4 }
                        series4.add(items[6].toInt(),items[7].toInt()).takeIf { items.size > 6 }
                        series5.add(items[8].toInt(),items[9].toInt()).takeIf { items.size > 8 }
                        series6.add(items[10].toInt(),items[11].toInt()).takeIf { items.size > 10 }
                        series7.add(items[12].toInt(),items[13].toInt()).takeIf { items.size > 12 }
                        series8.add(items[14].toInt(),items[15].toInt()).takeIf { items.size > 14 }
                    }
                }
                //countOfLine++
            }
            br.close()
        } catch (e: Exception) {
            logError("error +${e.message}")
            showMeSnackBar("Error Chart:  ${e.message}",Color.Red)
        }


        //dataset.notify = true
        series1.notify = true
        series2.notify = true
        series3.notify = true
        series4.notify = true
        series5.notify = true
        series6.notify = true
        series7.notify = true
        series8.notify = true



        if (withStandard) {

        }
        isLoading.value = false
    }

    fun refreshSeries() {

    }
}
