package ui.charts

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYSplineRenderer
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import utils.Dir1Configs
import utils.OPERATOR_ID
import utils.generateTimestampLastUpdate
import java.awt.Font
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.swing.BoxLayout
import javax.swing.JFileChooser
import javax.swing.JPanel

@Composable
fun chartWindow() {
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
fun ChartField() {
//    val dataset = createDataset2()
//    val chart = createChart(dataset!!)
//    val chartPanel = ChartPanel(chart)
//    chartPanel.border = BorderFactory.createEmptyBorder(15, 15, 15, 15)

    //create the series - add some dummy data
    val series1 = XYSeries("Pressure (Bar)")
    val series2 = XYSeries("PWM (A)")
    var step1 = 0.0
    var step2 = 0.0
//    for (i in 0 until 100) {
//        series1.add(step1, (0..12).random().toDouble()+step1)
//        step1+= 0.5
//    }


//    series1.add(1150.0, 1150.0)
//    series1.add(1250.0, 1250.0)

    for (i in 0 until 100) {
        series1.add(step1, (0..5).random().toDouble())
        step1+= 0.5

        series2.add(step2, (0..5).random().toDouble())
        step2+= 0.5
    }
//    series2.add(1000.0, 111250.0)
//    series2.add(1150.0, 211250.0)
//    series2.add(1250.0, 311250.0)

    //create the datasets

    //create the datasets
    val dataset1 = XYSeriesCollection()
    val dataset2 = XYSeriesCollection()
    dataset1.addSeries(series1)
    dataset2.addSeries(series2)

    //construct the plot
    val plot = XYPlot()
    plot.setDataset(0, dataset1)
    plot.setDataset(1, dataset2)


    //customize the plot with renderers and axis

    //customize the plot with renderers and axis
    //plot.setRenderer(0, XYSplineRenderer()) //use default fill paint for first series
    //plot.setRenderer(1, XYSplineRenderer()) //use default fill paint for first series

    var splinerenderer = XYSplineRenderer()
//    splinerenderer.setSeriesFillPaint    (0, java.awt.Color.BLUE)
//    splinerenderer.setSeriesShapesVisible(0,true)
//    splinerenderer.setSeriesPaint(0,java.awt.Color.BLUE)

    //splinerenderer.setSeriesStroke(0, BasicStroke(2.0f));
    var splinerenderer2 = XYSplineRenderer()
//    splinerenderer2.setSeriesFillPaint    (1, java.awt.Color.CYAN)
//    splinerenderer2.setSeriesShapesVisible(1,true)
//    splinerenderer2.setSeriesPaint(1,java.awt.Color.CYAN)


    plot.setRenderer(0, splinerenderer)
    plot.setRenderer(1, splinerenderer2)

    plot.setRangeAxis(0, NumberAxis(series1.key.toString()))
    plot.setRangeAxis(1, NumberAxis(series2.key.toString()))
    plot.domainAxis = NumberAxis("Time (seconds)")


    //Map the data to the appropriate axis

    //Map the data to the appropriate axis
    plot.mapDatasetToRangeAxis(0, 0)
    plot.mapDatasetToRangeAxis(1, 1)

    //generate the chart
    val chart = JFreeChart("Name of chart", Font("Serif", Font.BOLD, 18), plot, true)

    chart.backgroundPaint = java.awt.Color.WHITE
    val chartPanel: JPanel = ChartPanel(chart)


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(800.dp)){
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
        Row {
            Button(
                modifier = Modifier.width(100.dp).height(100.dp),
                onClick = {

                },
                // Uses ButtonDefaults.ContentPadding by default
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                )
            ) {

                Text("Like")
            }
        }

    }

    CoroutineScope(Dispatchers.IO).launch {
        delay(2000)
        print("pizdec!!")
        try {
            val theDir = File("${JFileChooser().fileSystemView.defaultDirectory.toString()}\\agregatka_machinetool")
            if (!theDir.exists()) {
                theDir.mkdirs()
            }
            val out: OutputStream = FileOutputStream(File("${JFileChooser().fileSystemView.defaultDirectory.toString()}\\agregatka_machinetool\\chart.png"))
            ChartUtils.writeChartAsPNG(
                out,
                chart,
                chartPanel.getWidth(),
                chartPanel.getHeight()
            )
        } catch (ex: IOException) {
            print("ERROR ${ex.message}")
        }
    }
}

@Composable
fun ChartSecond() {
    val dataset = XYSeriesCollection()
    val series1 = XYSeries("Эксперимент")
    val series2 = XYSeries("Стандарт, Эталон")
    val series3 = XYSeries("Object 3")
    val series4 = XYSeries("Object 4")

    //experiment:
    getChartFromFile(Dir1Configs).forEachIndexed { index, pointer ->
        series1.add(pointer.x,pointer.y)
    }

    //standard:
    getChartFromFile(Dir1Configs).forEachIndexed { index, pointer ->
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

    val chart: JFreeChart = ChartFactory.createXYLineChart(
        "Сравнение с эталоном [${generateTimestampLastUpdate()},${OPERATOR_ID}] ",
        "time (ms)", "Bar", dataset,
        PlotOrientation.VERTICAL, true, true, false
    )

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