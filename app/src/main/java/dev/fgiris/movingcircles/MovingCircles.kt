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
    // Lets move the circles from center by the half of the distance to center
//    val movementDistanceState = animateFloatAsState(
//        targetValue = 400f,
//        animationSpec = tween(
//            durationMillis = 10000,
//            easing = LinearEasing
//        )
//    )

    // Initial transition state
    val circleTransitionState = remember {
        mutableStateOf(MovingCirclesAnimationState.COLLAPSED)
    }

    // Create the transition
    val circleTransition = updateTransition(circleTransitionState.value)

    // Define the animation
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
            movementDistance = movementDistanceState.value
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
    movementDistance: Float
) {
    // We will use this to shift the center og the each circle
    val angleBetweenCircles = Math.toRadians(360.0 / numberOfCircles).toFloat()

    // Used to draw circles to the right place by adding angleBetweenCircles to the currentAngle
    var currentAngle = 0f

    repeat(numberOfCircles) {
        val x = centerOfCircles.x + (distanceToCenter + movementDistance) * sin(currentAngle)
        val y = centerOfCircles.y + (distanceToCenter + movementDistance) * cos(currentAngle)

        // Draw the circle
        drawScope.drawCircle(
            color = circleColor,
            center = Offset(x, y),
            radius = circleRadius
        )

        currentAngle += angleBetweenCircles
    }
}

enum class MovingCirclesAnimationState {
    COLLAPSED, EXPANDED
}