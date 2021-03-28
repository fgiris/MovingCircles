package dev.fgiris.movingcircles

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    // Create the transition
    val circleExplodeAnimatable = remember {
        Animatable(initialValue = 0f)
    }

    val circleRotationAnimatable = remember {
        Animatable(initialValue = 0f)
    }

    val circleAlphaAnimatable = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(Unit) {
        val explodeAnimationDuration = 2000L
        val fadeOutAnimationDuration = 1000L

        // During the whole animation, there will be rotation
        // So total animation duration = rotate animation duration
        val rotateAnimationDuration = 4000L

        // Kind of hack about delay. Fade in should be happening for the last 1 sec
        // This delay is used after explode animation in order to wait for the last
        // 1 sec of the animation
        val fadeOutDelayDuration = rotateAnimationDuration - (fadeOutAnimationDuration + explodeAnimationDuration)

        // Start explode and rotate animation at the same time
        // launching different coroutines for each
        launch {
            circleExplodeAnimatable.animateTo(
                targetValue = distanceToCenter / 2,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                )
            )
            // Need to delay for the difference between the
            delay(fadeOutDelayDuration)

            circleAlphaAnimatable.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                )
            )
        }

        launch {
            circleRotationAnimatable.animateTo(
                targetValue = -20f,
                animationSpec = tween(
                    durationMillis = 4000,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Canvas(modifier = modifier) {
        drawCircles(
            drawScope = this,
            numberOfCircles = numberOfCircles,
            centerOfCircles = size.center,
            distanceToCenter = distanceToCenter,
            circleColor = circleColor,
            circleRadius = circleRadius,
            circleExplodeAnimatableValue = circleExplodeAnimatable.value,
            rotationAngle = circleRotationAnimatable.value,
            alpha = circleAlphaAnimatable.value
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
    circleExplodeAnimatableValue: Float,
    rotationAngle: Float,
    alpha: Float
) {
    // We will use this to shift the center og the each circle
    val angleBetweenCircles = Math.toRadians(360.0 / numberOfCircles).toFloat()

    val rotationAngleInRadians = Math.toRadians(rotationAngle.toDouble()).toFloat()

    // Used to draw circles to the right place by adding angleBetweenCircles to the currentAngle
    var currentAngle = 0f + rotationAngleInRadians

    repeat(numberOfCircles) {
        val x =
            centerOfCircles.x + (distanceToCenter + circleExplodeAnimatableValue) * sin(currentAngle)
        val y =
            centerOfCircles.y + (distanceToCenter + circleExplodeAnimatableValue) * cos(currentAngle)

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
