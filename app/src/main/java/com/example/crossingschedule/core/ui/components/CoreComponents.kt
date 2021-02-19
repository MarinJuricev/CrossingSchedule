package com.example.crossingschedule.presentation.core.components

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.feature.schedule.presentation.model.AnimatedContainerState

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

val containerHeight = DpPropKey(label = "containerHeight")
val alphaPropKey = FloatPropKey(label = "alphaPropKey")

//@Deprecated("Please use updateTransition or rememberInfiniteTransition instead.")


val transitionDefinition = transitionDefinition<AnimatedContainerState> {
    state(AnimatedContainerState.IDLE) {
        this[containerHeight] = 36.dp
        this[alphaPropKey] = 0f
    }

    state(AnimatedContainerState.PRESSED) {
        this[containerHeight] = 120.dp
        this[alphaPropKey] = 1f
    }

    state(AnimatedContainerState.DO_NOT_ANIMATE) {
        this[containerHeight] = 36.dp
        this[alphaPropKey] = 0f
    }

    transition(fromState = AnimatedContainerState.IDLE, toState = AnimatedContainerState.PRESSED) {
        containerHeight using tween(durationMillis = 750)
        alphaPropKey using tween(
            durationMillis = 1000,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        )
    }

    transition(fromState = AnimatedContainerState.PRESSED, toState = AnimatedContainerState.IDLE) {
        containerHeight using tween(durationMillis = 750)
        alphaPropKey using tween(
            durationMillis = 1000,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        )
    }
}

@Composable
fun AnimatedContainer(
    animationState: State<AnimatedContainerState>,
    nonExpandedContent: @Composable () -> Unit,
    expandedContent: @Composable () -> Unit
) {
    val containerAnimationState = remember { MutableTransitionState(AnimatedContainerState.DO_NOT_ANIMATE) }

//    val transitionState = MutableTransitionState(animationState)
    val transition = updateTransition(targetState = animationState.value)
    val containerHeight by transition.animateDp(
        transitionSpec = {
            if()
        }
    ) {

    }

    val toState = when (animationState.value) {
        AnimatedContainerState.IDLE -> AnimatedContainerState.PRESSED
        AnimatedContainerState.DO_NOT_ANIMATE -> AnimatedContainerState.DO_NOT_ANIMATE
        else -> AnimatedContainerState.IDLE
    }

    val animatedContainerTransition = transition(
        definition = transitionDefinition,
        initState = animationState.value,
        toState = toState,
    )

    Column(
        modifier = Modifier
            .height(animatedContainerTransition[containerHeight]),
    ) {
        nonExpandedContent()
        if (animationState.value == AnimatedContainerState.IDLE) {
            Box(
                modifier = Modifier
                    .alpha(animatedContainerTransition[alphaPropKey])
            ) {
                expandedContent()
            }
        }
    }
}