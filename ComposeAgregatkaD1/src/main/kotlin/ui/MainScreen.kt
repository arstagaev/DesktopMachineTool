import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.CategoryPlot
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.AreaRendererEndType
import org.jfree.chart.renderer.category.AreaRenderer
import org.jfree.chart.renderer.xy.XYSplineRenderer
import org.jfree.chart.title.TextTitle
import org.jfree.data.category.CategoryDataset
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DatasetUtils
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import ui.parts_of_screen.center.centerPiece
import ui.parts_of_screen.leftPiece
import ui.parts_of_screen.rightPiece
import utils.longForChart
import java.awt.Font
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.swing.BoxLayout
import javax.swing.JFileChooser
import javax.swing.JPanel
import kotlin.concurrent.fixedRateTimer


//import VariablesUSB.*

//val serialPort: SerialPort = SerialPort.getCommPort("COM3")
var visiMainScr = mutableStateOf(true)
var showmeSnackBar = mutableStateOf(false)
var textForSnackBar = mutableStateOf("Alert")
var textForSnackBarColor = mutableStateOf(Color.Red)

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    var PRESSURE by remember { mutableStateOf(0) }
    var visibilityOfMainScreen by remember { visiMainScr }


    fixedRateTimer("timer", false, 5000L,2000) {
        PRESSURE = (0..90).random()
        //print("well ${PRESSURE}")
        //visibilityOfToast = !visibilityOfToast

    }

    var Height = 0
    var Width = 0
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().onGloballyPositioned {
            Height = it.size.height
            Width = it.size.width
            //println("W:${Width} H:${Height}")
        }) {
            Row{
                leftPiece(visibilityOfMainScreen)
                AnimatedVisibility(visible = visibilityOfMainScreen) {
                    centerPiece(PRESSURE,(PRESSURE*0.4).toInt(),1,1,11,23,1,1)
                }
                AnimatedVisibility(visible = !visibilityOfMainScreen) {
                    chartX()
                }
                rightPiece()
            }

            snackBarShow()


        }

    }
    showMeSnackBar("Приветствую!",Color.White)
}

fun showMeSnackBar(msg : String,color: Color) {
    textForSnackBar.value = msg
    showmeSnackBar.value = true
    textForSnackBarColor.value = color
}

@Composable
fun snackBarShow() {
    var visibilityOfToast by remember { showmeSnackBar }

    AnimatedVisibility(
        visible = visibilityOfToast,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.Black.copy(alpha = 0.6f))
        ) {
            Text("${textForSnackBar.value}", fontSize = 40.sp,modifier = Modifier.fillMaxSize().padding(30.dp), color =textForSnackBarColor.value)
        }
        GlobalScope.launch {
            delay(3000)
            visibilityOfToast = false
        }
    }
}

@Composable
fun chartView() {
    Column(
      modifier = Modifier.width(200.dp).fillMaxHeight().background(Color.Yellow)
    ) {
        Row(
            modifier = Modifier
                //.width(IntrinsicSize.Max)
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 5.dp)
            //.background(Color.Red)
            , horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearChart(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.Black),LinearChartStyle.Smooth,
                listOf(1f,2f,3f,4f,5f,6f), Color(255, 0, 0)
            )
        }
    }
}

@Composable
fun chartX() {
    var visibilityOfMainScreen by remember { visiMainScr }
//    val dataset = createDataset2()
//    val chart = createChart(dataset!!)
//    val chartPanel = ChartPanel(chart)
//    chartPanel.border = BorderFactory.createEmptyBorder(15, 15, 15, 15)

    //create the series - add some dummy data
    val series1 = XYSeries("Pressure (Bar)")
    val series2 = XYSeries("PWM (A)")
    var step1 = 0.0
//    for (i in 0 until 100) {
//        series1.add(step1, (0..12).random().toDouble()+step1)
//        step1+= 0.5
//    }
    print("*** ${longForChart.joinToString()}")
    for (i in 0 until longForChart.size) {
        series1.add(step1, longForChart[i])
        step1+= 0.01
    }

//    series1.add(1150.0, 1150.0)
//    series1.add(1250.0, 1250.0)
    var step2 = 0.0
    for (i in 0 until 100) {
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

    //construct the plot
    val plot = XYPlot()
    plot.setDataset(0, dataset1)
    plot.setDataset(1, dataset2)

    //customize the plot with renderers and axis

    //customize the plot with renderers and axis
    plot.setRenderer(0, XYSplineRenderer()) //use default fill paint for first series
    plot.setRenderer(1, XYSplineRenderer()) //use default fill paint for first series

    val splinerenderer = XYSplineRenderer()
    splinerenderer.setSeriesFillPaint(0, java.awt.Color.BLUE)
    splinerenderer.setSeriesShapesVisible(0,false)

    splinerenderer.setSeriesFillPaint    (1, java.awt.Color.RED)
    splinerenderer.setSeriesShapesVisible(1,false)

    plot.setRenderer(1, splinerenderer)
    plot.setRenderer(0, splinerenderer)

    plot.setRangeAxis(0, NumberAxis(series1.key.toString()))
    plot.setRangeAxis(1, NumberAxis(series2.key.toString()))
    plot.domainAxis = NumberAxis("Time (seconds)")


    //Map the data to the appropriate axis

    //Map the data to the appropriate axis
    plot.mapDatasetToRangeAxis(0, 0)
    plot.mapDatasetToRangeAxis(1, 1)

    //generate the chart

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
                modifier = Modifier.size(width = 1000.dp, height =  600.dp)
                    ,
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

    CoroutineScope(Dispatchers.Main).launch {
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

///////
fun createDataset(): CategoryDataset? {
    var data = arrayOf(
        doubleArrayOf(
            1.0,
            2.0,
            1.0,
            1.0,
            1.0,
            1.0,
            1.0,
            1.0,
            1.0,
            1.0
        )
    )

    var pzc = arrayListOf<Double>()
    var time = arrayListOf<Double>()
    var step = 0.0
    for (i in 0..100) {

        pzc.add(1.0/(0..10).random().toDouble())
        time.add(step)
        step += 0.5
    }
    var asd = pzc.toDoubleArray()
    var dfs = arrayOf(time)
    val dataset = DefaultCategoryDataset()
    dataset.addValue(10.0,"","")
    return DatasetUtils.createCategoryDataset(
        arrayOf("Oil"), arrayOf(), arrayOf(
        asd
        ))
}

private fun createDataset2(): XYSeriesCollection {
    val iexplorer = XYSeries("InternetExplorer")
    iexplorer.add(3.0, 4.0)
    iexplorer.add(4.0, 5.0)
    iexplorer.add(5.0, 4.0)


    val dataset = XYSeriesCollection()
//    dataset.addSeries(firefox)
//    dataset.addSeries(chrome)
    dataset.addSeries(iexplorer)
    return dataset
}

private fun createChart(dataset: CategoryDataset): JFreeChart? {
    val chart: JFreeChart = ChartFactory.createAreaChart(
        "Oil consumption",
        "Time",
        "Thousands bbl/day",
        dataset,
        PlotOrientation.VERTICAL,
        false,
        true,
        true
    )
    val plot: CategoryPlot = chart.getPlot() as CategoryPlot
    plot.setForegroundAlpha(0.3f)
    val renderer: AreaRenderer = plot.getRenderer() as AreaRenderer
    renderer.setEndType(AreaRendererEndType.LEVEL)
    chart.setTitle(
        TextTitle(
            "Oil consumption",
            Font("Serif", Font.BOLD, 18)
        )
    )

    return chart
}


//
//private fun initSerialCommunication() {
//    println(">>>serial communication has been started")
//    serialPort.baudRate = 115200
//    serialPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY)
//    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0)
//    serialPort.openPort()
//
//    var a = 0
//    val bb = byteArrayOf(0x74.toByte(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
//    try {
//        Thread.sleep(2000)
//    } catch (e: InterruptedException) {
//        e.printStackTrace()
//    }
//    while (a < 10) {
//        serialPort.writeBytes(bb, 1)
//        //            try {
//        println("goo " + bb.size)
//        //                serialPort.getOutputStream().write(bb);
////                //serialPort.getOutputStream().flush();
////            } catch (IOException e) {
////                System.out.println("pizdec "+e.getMessage());
////                e.printStackTrace();
////            }
//        a++
//    }
//
//    serialPort.addDataListener(object : SerialPortDataListener {
//        override fun getListeningEvents(): Int {
//            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE
//        }
//
//        override fun serialEvent(event: SerialPortEvent) {
//            if (event.eventType != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
//                sound(-1)
//                return
//            }
//            newData = ByteArray(serialPort.bytesAvailable())
//            val numRead = serialPort.readBytes(newData, newData.size.toLong())
//            //var nnn = uintArrayOf(newData)
//            //nnn =
//
//            println("conv " + (newData.toUByteArray()).joinToString() + "[[ ${newData.size}")
//            //curPoint =
//            firstAnalog( filterKalman( onesAndTens(newData[0],newData[1])))
//            secondAnalog(onesAndTens(newData[2],newData[3]))
//            thirdAnalog( onesAndTens(newData[4],newData[5]))
//            fourthAnalog(onesAndTens(newData[6],newData[7]))
//
//            fiveAnalog( onesAndTens(newData[8],newData[9]))
//            sixAnalog(  onesAndTens(newData[10],newData[11]))
//            sevenAnalog(onesAndTens(newData[12],newData[13]))
//            eightAnalog(onesAndTens(newData[14],newData[15]))
//            z++
//        }
//    })
//}
//
//fun stopSerialCommunication(){
//    serialPort.removeDataListener()
//    serialPort.closePort()
//}