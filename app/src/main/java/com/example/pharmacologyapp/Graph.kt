package com.example.pharmacologyapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.Point
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Generates a line graph composable from a list of points.
 *
 * @param modifier Modifier to be applied
 * @param pointsData List of points to be graphed
 * @param yMax The maximum height of the graph
 * @param ySteps The number of steps on the Y axis
 */
@Composable
fun LineGraph(modifier: Modifier = Modifier, pointsData:List<Point>, yMax:Float, ySteps:Int){

    val xAxisData = AxisData.Builder()
        .axisStepSize(25.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = yMax / ySteps
            (i * yScale).toString()
        }
        .build()

    val lineColor = Color(0xFF2AFFEE) // Cyan

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(color = lineColor),
                    intersectionPoint = IntersectionPoint(color = lineColor, radius = 3.dp),
                    selectionHighlightPoint = SelectionHighlightPoint(color = Color.Black, radius = 3.dp),
                    shadowUnderLine = ShadowUnderLine(color = lineColor, alpha = 0.2f),
                    selectionHighlightPopUp = SelectionHighlightPopUp(popUpLabel = { x, y ->
                        val xLabel = "Time: ${x.toString()}s "
                        val yLabel = "Tension: ${String.format("%.2f", y)}g"
                        "$xLabel $yLabel"
                    })

                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = Color.Transparent
    )
    Surface(modifier = modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier
                .fillMaxSize(),
            lineChartData = lineChartData
        )
    }

}

/**
 * Clears out all points from a list of points except the first two
 *
 * @param pointData The list of points
 */
fun resetPointData(pointData: SnapshotStateList<Point>) {
    if (pointData.size > 2) {
        val firstPoint = pointData[0]
        val secondPoint = pointData[1]
        pointData.clear()
        pointData.add(firstPoint)
        pointData.add(secondPoint)
    }
}

@Composable
fun MasterGraph(modifier: Modifier = Modifier) {
    val pointData = remember { mutableStateListOf<Point>(Point(0f,0f)) } // Need to be a mutable state to re-render on change
    val scope = rememberCoroutineScope()
    var isGenerating by remember { mutableStateOf<Boolean>(false) }
    var isPaused by remember { mutableStateOf<Boolean>(false) }
    var job: Job?  by remember { mutableStateOf(null)}

    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            Row(modifier = modifier
                .fillMaxSize()
                .weight(1f)
            ) {
                Button(onClick = {
                    if (!isGenerating) {
                        isGenerating = true
                        isPaused = false
                        job = scope.launch {
                            for (i in pointData.size until 1200) {
                                if (!isGenerating) break
                                pointData.add(Point(i * 0.5f, (Math.random() * 100).toFloat()))
                                delay(500) // Half second delay
                            }
                            isGenerating = false
                        }
                    } else {
                        isPaused = !isPaused
                        if (isPaused) {
                            job?.cancel()
                        } else {
                            job = scope.launch {
                                for (i in pointData.size until 1200) {
                                    if (!isGenerating) break
                                    pointData.add(Point(i * 0.5f, (Math.random() * 100).toFloat()))
                                    delay(500) // Half second delay
                                }
                                isGenerating = false
                            }
                        }
                    }
                }) {
                    Text(if (isGenerating && !isPaused) "Pause" else "Start/Resume")
                }

                if (isGenerating) {
                    Button(onClick = {
                        isPaused = true
                        isGenerating = false
                        job?.cancel()
                    }) {
                        Text("Stop")
                    }
                }
            }
            Row(modifier = modifier
                .fillMaxSize()
                .weight(4f)
            ) {
                LineGraph(modifier = modifier.fillMaxSize(), pointData, yMax = 100f, ySteps = 10)
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 620, heightDp = 320)
@Composable
fun YChartGraphPreview(
    modifier: Modifier = Modifier
) {
    MasterGraph()
}