package dev.fgiris.movingcircles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.fgiris.movingcircles.ui.theme.MovingCirclesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovingCirclesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MovingCircles(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        numberOfCircles = 12,
                        distanceToCenter = 100f,
                        circleColor = Color.Black,
                        circleRadius = 10f
                    )
                }
            }
        }
    }
}
