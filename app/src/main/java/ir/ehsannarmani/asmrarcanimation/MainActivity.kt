package ir.ehsannarmani.asmrarcanimation

import android.graphics.Paint.Cap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.asmrarcanimation.ui.theme.AsmrArcAnimationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AsmrArcAnimationTheme {

                val arcStart = remember {
                    mutableFloatStateOf(0f)
                }
                val animatedStart = animateFloatAsState(
                    targetValue = arcStart.value,
                    animationSpec = tween(5000, easing = EaseInOutCubic),
                    finishedListener = {
                        arcStart.value += 1f
                    })
                LaunchedEffect(Unit) {
                    arcStart.value = 1f
                }
                val infiniteTransition = rememberInfiniteTransition()
                val arcSweep = infiniteTransition.animateFloat(
                    initialValue = 0.001f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(3000, easing = EaseInOutCubic),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                val pathMeasure = remember{
                    PathMeasure()
                }



                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp), contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .graphicsLayer {
                                rotationY = 35f*arcSweep.value
                                rotationX = -35f*arcSweep.value
                            }
                        ) {
                            val x = (size.width / 2)
                            val y = (size.height / 2)

                            rotate(arcSweep.value*360f){

                                val circle1 = Path().apply {
                                    arcTo(
                                        rect = Rect(
                                            center = Offset(
                                                x = x,
                                                y = y
                                            ),
                                            radius = (size.width / 1.7F)- (200f*arcSweep.value)
                                        ),
                                        startAngleDegrees = 360f*animatedStart.value,
                                        sweepAngleDegrees = 360f*arcSweep.value,
                                        forceMoveTo = true
                                    )
                                }

                                var segmentedPath = Path()
                                pathMeasure.setPath(circle1,false)
                                pathMeasure.getSegment((1-arcSweep.value)-10f,arcSweep.value*pathMeasure.length,segmentedPath,true)

                                drawPath(
                                    path = segmentedPath,
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFFFA726),
                                            Color(0xFFFFEE58)
                                        )
                                    ),
                                    style = Stroke(width = (80f)-(40f*arcSweep.value),cap = StrokeCap.Round, pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(80f,80f),
                                        phase = 80f
                                    ))
                                )
                                val circle2 = Path().apply {
                                    arcTo(
                                        rect = Rect(
                                            center = Offset(
                                                x = x,
                                                y = y
                                            ),
                                            radius = (size.width / 2)- (200f*arcSweep.value)
                                        ),
                                        startAngleDegrees = -360f*animatedStart.value,
                                        sweepAngleDegrees = 360f*arcSweep.value,
                                        forceMoveTo = true
                                    )
                                }
                                segmentedPath = Path()
                                pathMeasure.setPath(circle2,false)
                                pathMeasure.getSegment((1-arcSweep.value)-10f,arcSweep.value*pathMeasure.length,segmentedPath,true)

                                drawPath(
                                    path = segmentedPath,
                                    brush = Brush.linearGradient(listOf(
                                        Color(0xFFEF5350),
                                        Color(0xFFEC407A)
                                    )),
                                    style = Stroke(width = (80f)-(40f*arcSweep.value),cap = StrokeCap.Round)
                                )

                                val circle3 = Path().apply {
                                    arcTo(
                                        rect = Rect(
                                            center = Offset(
                                                x = x,
                                                y = y
                                            ),
                                            radius = (size.width / 2.5f)- (200f*arcSweep.value)
                                        ),
                                        startAngleDegrees = 360f*animatedStart.value,
                                        sweepAngleDegrees = 360f*arcSweep.value,
                                        forceMoveTo = true
                                    )
                                }
                                segmentedPath = Path()
                                pathMeasure.setPath(circle3,false)
                                pathMeasure.getSegment((1-arcSweep.value)-10f,arcSweep.value*pathMeasure.length,segmentedPath,true)

                                drawPath(
                                    path = segmentedPath,
                                    brush = Brush.linearGradient(listOf(
                                        Color(0xFF7E57C2),
                                        Color(0xFFAB47BC)
                                    )),
                                    style = Stroke(width = (80f)-(40f*arcSweep.value),cap = StrokeCap.Round)
                                )

                                val circle4 = Path().apply {
                                    arcTo(
                                        rect = Rect(
                                            center = Offset(
                                                x = x,
                                                y = y
                                            ),
                                            radius = (size.width / 3.3f)- (200f*arcSweep.value)
                                        ),
                                        startAngleDegrees = -360f*animatedStart.value,
                                        sweepAngleDegrees = 360f*arcSweep.value,
                                        forceMoveTo = true
                                    )
                                }
                                segmentedPath = Path()
                                pathMeasure.setPath(circle4,false)
                                pathMeasure.getSegment((1-arcSweep.value)-10f,arcSweep.value*pathMeasure.length,segmentedPath,true)

                                drawPath(
                                    path = segmentedPath,
                                    brush = Brush.linearGradient(listOf(
                                        Color(0xFF66BB6A),
                                        Color(0xFF9CCC65)
                                    )),
                                    style = Stroke(width = (80f)-(40f*arcSweep.value),cap = StrokeCap.Round)
                                )

                                val circle5 = Path().apply {
                                    arcTo(
                                        rect = Rect(
                                            center = Offset(
                                                x = x,
                                                y = y
                                            ),
                                            radius = (size.width / 4.8f)- (200f*arcSweep.value)
                                        ),
                                        startAngleDegrees = 360f*animatedStart.value,
                                        sweepAngleDegrees = 360f*arcSweep.value,
                                        forceMoveTo = true
                                    )
                                }

                                segmentedPath = Path()
                                pathMeasure.setPath(circle5,false)
                                pathMeasure.getSegment((1-arcSweep.value)-10f,arcSweep.value*pathMeasure.length,segmentedPath,true)

                                drawPath(
                                    path = segmentedPath,
                                    brush = Brush.linearGradient(listOf(
                                        Color(0xFF26A69A),
                                        Color(0xFF26C6DA)
                                    )),
                                    style = Stroke(width = (80f)-(40f*arcSweep.value), cap = StrokeCap.Round),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
fun calculatePercentageInRange(value: Double, minValue: Double, maxValue: Double): Double {

    val range = maxValue - minValue
    val positionInRange = value - minValue
    val percentage = (positionInRange / range) * 100

    return percentage
}

