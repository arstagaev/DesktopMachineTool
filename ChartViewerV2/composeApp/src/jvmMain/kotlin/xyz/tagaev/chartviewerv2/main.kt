package com.example.chartviewer

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.rememberFloatLinearAxisModel
import io.github.koalaplot.core.style.LineStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import io.github.koalaplot.core.line.LinePlot
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xygraph.Point
import io.github.koalaplot.core.xygraph.autoScaleXRange
import io.github.koalaplot.core.xygraph.autoScaleYRange
import java.awt.FileDialog
import java.io.File

/**
 * Reads a text file where each line contains up to 8 X;Y pairs separated by '|'.
 * Returns a list of series (each series is a List<Pair<Double, Double>>).
 */
fun loadData(path: String): List<List<Pair<Double, Double>>>? {
    return try {
        File(path).bufferedReader().useLines { lines ->
            val rows = lines.map { line ->
                line.split("|").mapNotNull { segment ->
                    segment.split(";").takeIf { it.size == 2 }
                        ?.let { (xs, ys) ->
                            xs.toDoubleOrNull()?.let { x ->
                                ys.toDoubleOrNull()?.let { y -> x to y }
                            }
                        }
                }
            }.toList()
            if (rows.isEmpty()) emptyList()
            else (0 until rows[0].size).map { col ->
                rows.mapNotNull { row -> row.getOrNull(col) }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun ChartView(
    dataSeries: List<List<Pair<Double, Double>>>,
    title: String
) {
    // 1) Convert Double-pairs into Point<Float,Float>
    val floatSeries: List<List<Point<Float, Float>>> = dataSeries.map { series ->
        series.map { (x, y) ->
            Point(x.toFloat(), y.toFloat())
        }
    }

    // 2) Auto-scale based on all points
    val allPoints = floatSeries.flatten()
    val xModel   = rememberFloatLinearAxisModel(allPoints.autoScaleXRange())
    val yModel   = rememberFloatLinearAxisModel(allPoints.autoScaleYRange())


    XYGraph(
        xModel,
        yModel
    ) {
        floatSeries.forEachIndexed { idx, series ->
            LinePlot(
                series,
                lineStyle = when (idx) {
                    0 -> LineStyle(SolidColor(Color.Blue))
                    1 -> LineStyle(
                        SolidColor(Color(0, 200, 0)),
                        strokeWidth = 2.dp,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f))
                    )
                    else -> LineStyle(SolidColor(Color.Gray))
                }
            )
        }
    }
}

@Preview
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "KoalaPlot v0.9.0 Viewer") {
        var data1 by remember { mutableStateOf<List<List<Pair<Double, Double>>>?>(null) }
        var data2 by remember { mutableStateOf<List<List<Pair<Double, Double>>>?>(null) }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {
                    FileDialog(null as java.awt.Frame?, "Select File 1", FileDialog.LOAD).apply {
                        isVisible = true
                        file?.let { fn ->
                            loadData("$directory$fn")?.let { data1 = it }
                        }
                    }
                }) {
                    Text("Load File 1")
                }

                Button(onClick = {
                    FileDialog(null as java.awt.Frame?, "Select File 2", FileDialog.LOAD).apply {
                        isVisible = true
                        file?.let { fn ->
                            loadData("$directory$fn")?.let { data2 = it }
                        }
                    }
                }) {
                    Text("Load File 2")
                }
            }

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                data1?.let { ChartView(it, "Dataset 1") }
                data2?.let { ChartView(it, "Dataset 2") }
            }
        }
    }
}
