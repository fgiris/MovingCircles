package dev.fgiris.movingcircles

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MovingCircles(
    modifier: Modifier,
    numberOfCircles: Int,
    distanceToCenter: Float,
    circleColor: Color,
    circleRadius: Float
) {
    Canvas(modifier = modifier) {
        drawCircles(
            drawScope = this,
            numberOfCircles = numberOfCircles,
            centerOfCircles = size.center,
            distanceToCenter = distanceToCenter,
            circleColor = circleColor,
            circleRadius = circleRadius
        )
    }
}

private fun drawCircles(
    drawScope: DrawScope,
    numberOfCircles: Int,
    centerOfCircles: Offset,
    distanceToCenter: Float,
    circleColor: Color,
    circleRadius: Float
) {
    // We will use this to shift the center og the each circle
    val angleBetweenCircles = Math.toRadians(360.0 / numberOfCircles).toFloat()

    // Used to draw circles to the right place by adding angleBetweenCircles to the currentAngle
    var currentAngle = 0f

    repeat(numberOfCircles) {
        val x = centerOfCircles.x + distanceToCenter * sin(currentAngle)
        val y = centerOfCircles.y + distanceToCenter * cos(currentAngle)

        // Draw the circle
        drawScope.drawCircle(
            color = circleColor,
            center = Offset(x, y),
            radius = circleRadius
        )

        currentAngle += angleBetweenCircles
    }
}