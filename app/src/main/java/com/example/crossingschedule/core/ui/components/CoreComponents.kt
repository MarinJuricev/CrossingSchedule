package com.example.crossingschedule.core.ui.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.feature.schedule.presentation.model.AnimatedContainerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CrossingCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = MaterialTheme.shapes.large,
    ) {
        content()
    }
}

@Composable
fun CrossingErrorCard(
    modifier: Modifier = Modifier,
    errorMessage: String
) {
    CrossingCard(modifier) {
        Text(
            modifier = Modifier.padding(24.dp),
            text = errorMessage,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun AnimatedContainer(
    animationState: State<AnimatedContainerState>,
    nonExpandedContent: @Composable () -> Unit,
    expandedContent: @Composable () -> Unit
) {
    val transition = updateTransition(targetState = animationState)

    val containerHeight by transition.animateDp(
        transitionSpec = {
            tween(durationMillis = 1000)
        }
    ) {
        when (it.value) {
            AnimatedContainerState.IDLE -> 36.dp
            AnimatedContainerState.PRESSED -> 120.dp
        }
    }

    val containerAlpha by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 1000)
        }
    ) {
        when (it.value) {
            AnimatedContainerState.IDLE -> 0f
            AnimatedContainerState.PRESSED -> 1f
        }
    }

    Column(
        modifier = Modifier
            .height(containerHeight),
    ) {
        nonExpandedContent()
        if (animationState.value == AnimatedContainerState.PRESSED) {
            Box(
                modifier = Modifier
                    .alpha(containerAlpha)
            ) {
                expandedContent()
            }
        }
    }
}

@Composable
fun PullToRefreshLoadingContent(
    isEmpty: Boolean,
    emptyContent: @Composable () -> Unit,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = onRefresh,
        content = if (isEmpty) emptyContent else content
    )
}