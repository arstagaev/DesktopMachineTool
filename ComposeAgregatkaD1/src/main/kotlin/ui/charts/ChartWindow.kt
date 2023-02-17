package ui.charts

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import enums.StateExperiments
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYItemRenderer
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import utils.*
import java.awt.BasicStroke
import java.io.*
import javax.swing.BoxLayout
import javax.swing.JPanel

class ChartWindowNew(var isStandard: Boolean = false) {

    @Composable
    fun chartWindow() {
        STATE_CHART.value = StateExperiments.PREPARE_CHART
        Window(
            title = "Compare with Standard ",
            state = WindowState(size = DpSize(1000.dp, 800.dp)),
            onCloseRequest = {

            },
        ) {
            ChartSecond()
            //ChartField()
        }
    }

    @Composable
    fun ChartSecond() {
        val dataset = XYSeriesCollection()

        val series1 = XYSeries("Давление 1"+(if(isStandard) " [Стандарт]" else ""))
        val series2 = XYSeries("Давление 2"+(if(isStandard) " [Стандарт]" else ""))
        val series3 = XYSeries("Давление 3"+(if(isStandard) " [Стандарт]" else ""))
        val series4 = XYSeries("Давление 4"+(if(isStandard) " [Стандарт]" else ""))
        val series5 = XYSeries("Давление 5"+(if(isStandard) " [Стандарт]" else ""))
        val series6 = XYSeries("Давление 6"+(if(isStandard) " [Стандарт]" else ""))
        val series7 = XYSeries("Давление 7"+(if(isStandard) " [Стандарт]" else ""))
        val series8 = XYSeries("Давление 8"+(if(isStandard) " [Стандарт]" else ""))

        //experiment:
        try {
            val br = BufferedReader(FileReader(chartFileAfterExperiment))
            var line: String?
            var countOfLine = 0
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
                countOfLine++
            }
            br.close()
        } catch (e: Exception) {
            logError("error +${e.message}")
        }

        //standard:
        getChartFromFile(Dir8).forEachIndexed { index, pointer ->
            series2.add(pointer.x,pointer.y)
        }

        if (series1.isEmpty) {
//        repeat(100) {
//            series1.add((0..100).random(),it)
//        }
//        repeat(100) {
//            series2.add( (0..100).random(),it)
//        }
            repeat(100) {
                series3.add((0..100).random(),it)
            }
            repeat(100) {
                series4.add((0..100).random(),it, )
            }
        }

        dataset.addSeries(series1)
        dataset.addSeries(series2)
        dataset.addSeries(series3)
        dataset.addSeries(series4)
        dataset.addSeries(series5)
        dataset.addSeries(series6)
        dataset.addSeries(series7)
        dataset.addSeries(series8)

        val xAxis = NumberAxis("time (ms)")
        xAxis.autoRangeIncludesZero = false
        val yAxis = NumberAxis("Bar")
        val renderer: XYItemRenderer = XYLineAndShapeRenderer(true, false)
        val plot = XYPlot(dataset, xAxis, yAxis, renderer)
        plot.setOrientation(PlotOrientation.VERTICAL)

        val arrClr = arrayOf(
            java.awt.Color.RED,
            java.awt.Color.ORANGE,
            java.awt.Color.PINK,
            java.awt.Color.YELLOW,
            java.awt.Color.GREEN,
            java.awt.Color.BLUE,
            java.awt.Color.CYAN,
            java.awt.Color.MAGENTA
        )

        repeat(8) {
            renderer. setSeriesPaint(it, arrClr[it])
            renderer.setSeriesStroke(it, BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND))

        }


        plot.setRenderer(renderer)
        val chart = JFreeChart(
            "Сравнение с эталоном [${generateTimestampLastUpdate()},${OPERATOR_ID}]", JFreeChart.DEFAULT_TITLE_FONT,
            plot,true
        )
        STATE_CHART.value = StateExperiments.NONE
//    val chart: JFreeChart = ChartFactory.createXYLineChart(
//        "Сравнение с эталоном [${generateTimestampLastUpdate()},${OPERATOR_ID}] ",
//        "time (ms)", "Bar", dataset,
//        PlotOrientation.VERTICAL, true, true, false
//    )

        val chartPanel: JPanel = ChartPanel(chart)

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(modifier = Modifier.fillMaxWidth().height(800.dp)) {
                SwingPanel(
                    background = Color.White,
                    modifier = Modifier.size(width = 1000.dp, height =  600.dp),
                    factory = {
                        JPanel().apply {
                            setLayout(BoxLayout(this, BoxLayout.Y_AXIS))
                            add(chartPanel)

                        }
                    }
                )
            }
        }
    }
}
