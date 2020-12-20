package com.example.crossingschedule

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.ui.CrossingScheduleTheme
import com.example.crossingschedule.ui.crossingTypography

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrossingScheduleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    Scaffold {
        Box {
            BackgroundImage(resourceId = R.drawable.home_background)
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(topRight = 16.dp, bottomRight = 16.dp),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
                            text = "Daily Checklist",
                            style = crossingTypography.h2,
                        )
                    }
                    Spacer(modifier = Modifier.padding(24.dp))
                    Column {
                        Card(
                            modifier = Modifier
                                .padding(vertical = 16.dp),
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 16.dp),
                                text = "Date: 18.12.2020",
                                style = crossingTypography.h6.copy(fontWeight = FontWeight.Bold),
                            )
                        }
                        RawIngredientRow()
                    }
                }
            }
        }
    }
}

@Composable
fun BackgroundImage(
    @DrawableRes resourceId: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentScale = ContentScale.FillBounds,
        bitmap = imageFromResource(
            AmbientContext.current.resources,
            resourceId
        )
    )
}

@Composable
fun RawIngredientRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Image(
            modifier = Modifier
                .size(36.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.tree_branch
            )
        )
        Image(
            modifier = Modifier
                .size(36.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.stone
            )
        )
        Image(
            modifier = Modifier
                .size(36.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.clay
            )
        )
        Image(
            modifier = Modifier
                .size(36.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.hard_wood
            )
        )
        Image(
            modifier = Modifier
                .size(36.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.iron_nugget
            )
        )
    }
}