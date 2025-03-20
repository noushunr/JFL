package com.newagesmb.androidmvvmarchitecture.utils

//
// Created by Noushad on 13-06-2024.
// Copyright (c) 2024 NewAgeSys. All rights reserved.
//
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Data class representing the style configuration for the stroke of the CircleLoader.
 *
 * @property width The width of the loader's stroke.
 * @property strokeCap The cap style for the stroke.
 * @property glowRadius Optional radius for adding a glow effect to the stroke.
 */
data class StrokeStyle(
    val width: Dp = 4.dp,
    val strokeCap: StrokeCap = StrokeCap.Round,
    val glowRadius: Dp? = 4.dp
)

/**
 * Composable function to render a circular loading indicator.
 *
 * @param modifier Modifier for the composable.
 * @param isVisible Determines whether the loader is visible or not.
 * @param color The primary color of the loader.
 * @param secondColor The secondary color of the loader.
 * @param tailLength Length of the loader's tail in degrees.
 * @param smoothTransition Indicates whether the transition between tail lengths should be smooth or immediate.
 * @param strokeStyle Style configuration for the stroke of the loader.
 * @param cycleDuration Duration of a single cycle of the loader animation in milliseconds.
 */
@Composable
fun CircleLoader(
    modifier: Modifier,
    isVisible: Boolean,
    color: Color,
    secondColor: Color? = color,
    tailLength: Float = 140f,
    smoothTransition: Boolean = true,
    strokeStyle: StrokeStyle = StrokeStyle(),
    cycleDuration: Int = 1400,
) {
    val tailToDisplay = remember { Animatable(0f) }

    LaunchedEffect(isVisible) {
        val targetTail = if (isVisible) tailLength else 0f
        when {
            smoothTransition -> tailToDisplay.animateTo(
                targetValue = targetTail,
                animationSpec = tween(cycleDuration, easing = LinearEasing)
            )

            else -> tailToDisplay.snapTo(targetTail)
        }
    }

    val transition = rememberInfiniteTransition(label = "")
    val spinAngel by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = cycleDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { /* Handle tap action */ }
                )
            }
            .background(
                color = Color.Transparent.copy(alpha = .5f),

                ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier
                .rotate(spinAngel)
                .aspectRatio(1f)
        ) {
            listOfNotNull(color, Color(0xFF029634)).forEachIndexed { index, color ->
                rotate(if (index == 0) 0f else 180f) {
                    val brush = Brush.sweepGradient(
                        0f to Color.Transparent,
                        tailToDisplay.value / 360f to color,
                        1f to Color.Transparent
                    )
                    val paint = setupPaint(strokeStyle, brush)

                    drawIntoCanvas { canvas ->
                        canvas.drawArc(
                            rect = size.toRect(),
                            startAngle = 0f,
                            sweepAngle = tailToDisplay.value,
                            useCenter = false,
                            paint = paint
                        )
                    }
                }
            }
        }
    }
}

/**
 * Sets up and configures the Paint object for drawing the loader.
 *
 * @param style Style configuration for the stroke.
 * @param brush Brush configuration for the paint.
 * @return Paint object configured with the provided stroke style and brush.
 */
private fun DrawScope.setupPaint(style: StrokeStyle, brush: Brush): Paint {
    val paint = Paint().apply paint@{
        this@paint.isAntiAlias = true
        this@paint.style = PaintingStyle.Stroke
        this@paint.strokeWidth = style.width.toPx()
        this@paint.strokeCap = style.strokeCap

        brush.applyTo(size, this@paint, 1f)
    }

    style.glowRadius?.let { radius ->
        paint.asFrameworkPaint().setShadowLayer(
            /* radius = */ radius.toPx(),
            /* dx = */ 0f,
            /* dy = */ 0f,
            /* shadowColor = */ android.graphics.Color.WHITE
        )
    }

    return paint
}