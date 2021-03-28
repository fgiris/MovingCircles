package dev.fgiris.movingcircles

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    // Initial transition state
    val circleTransitionState = remember {
        mutableStateOf(MovingCirclesAnimationState.COLLAPSED)
    }

    // Create the transition
    val circleTransition = updateTransition(circleTransitionState.value)

    // Circle expansion animation
    val movementDistanceState = circleTransition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            )
        }
    ) {
        if (it == MovingCirclesAnimationState.COLLAPSED) 0f else distanceToCenter / 2
    }

    // Circle rotation animation
    val rotationAngleState = circleTransition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 2500,
                easing = LinearEasing
            )
        }
    ) {
        if (it == MovingCirclesAnimationState.COLLAPSED) 0f else -20f
    }

    val circleAlphaState = circleTransition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 2500,
                easing = LinearEasing
            )
        }
    ) {
        if (it == MovingCirclesAnimationState.COLLAPSED) 1f else 0f
    }

    // Start the animation
    circleTransitionState.value = MovingCirclesAnimationState.EXPANDED

    Canvas(modifier = modifier) {
        drawCircles(
            drawScope = this,
            numberOfCircles = numberOfCircles,
            centerOfCircles = size.center,
            distanceToCenter = distanceToCenter,
            circleColor = circleColor,
            circleRadius = circleRadius,
            movementDistance = movementDistanceState.value,
            rotationAngle = rotationAngleState.value,
            alpha = circleAlphaState.value
        )
    }
}

private fun drawCircles(
    drawScope: DrawScope,
    numberOfCircles: Int,
    centerOfCircles: Offset,
    distanceToCenter: Float,
    circleColor: Color,
    circleRadius: Float,
    movementDistance: Float,
    rotationAngle: Float,
    alpha: Float
) {
    // We will use this to shift the center og the each circle
    val angleBetweenCircles = Math.toRadians(360.0 / numberOfCircles).toFloat()

    val rotationAngleInRadians = Math.toRadians(rotationAngle.toDouble()).toFloat()

    // Used to draw circles to the right place by adding angleBetweenCircles to the currentAngle
    var currentAngle = 0f + rotationAngleInRadians

    repeat(numberOfCircles) {
        val x = centerOfCircles.x + (distanceToCenter + movementDistance) * sin(currentAngle)
        val y = centerOfCircles.y + (distanceToCenter + movementDistance) * cos(currentAngle)

        // Draw the circle
        drawScope.drawCircle(
            color = circleColor,
            center = Offset(x, y),
            radius = circleRadius,
            alpha = alpha
        )

        currentAngle += angleBetweenCircles
    }
}

enum class MovingCirclesAnimationState {
    COLLAPSED, EXPANDED
}