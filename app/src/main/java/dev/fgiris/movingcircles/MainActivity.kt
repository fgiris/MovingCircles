package dev.fgiris.movingcircles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import dev.fgiris.movingcircles.ui.theme.MovingCirclesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovingCirclesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        MovingCircles(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            numberOfCircles = 32,
                            distanceToCenter = 200f,
                            circleColor = MaterialTheme.colors.onBackground,
                            circleRadius = 10f
                        )


                        Text(
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = "%50",
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 32.sp
                        )
                    }
                }
            }
        }
    }
}
