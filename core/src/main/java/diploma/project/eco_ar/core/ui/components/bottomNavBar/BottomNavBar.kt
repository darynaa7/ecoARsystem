package diploma.project.eco_ar.core.ui.components.bottomNavBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.ui.animations.smartAnimateFloatAsState
import diploma.project.eco_ar.core.ui.modifier.noIndicationClickable
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoutesBackStack
import diploma.project.eco_ar.core.ui.navigation.routes.MainRoutes
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    backStack: NestedRoutesBackStack
) {
    val colorTheme = LocalColorTheme.current

    val graphsWithStartRoutes = remember { MainRoutes.getMainRoutes() }

    val currentTopRoute = backStack.topRoutes.lastOrNull()

    val isVisible = remember(currentTopRoute, graphsWithStartRoutes, currentTopRoute?.children?.lastOrNull()) {
        currentTopRoute in graphsWithStartRoutes && currentTopRoute?.children?.size?.let { it < 2 } == true
    }

    val shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)

    val fadeAnimationSpec: FiniteAnimationSpec<Float> = remember {
        tween(
            durationMillis = 300,
            easing = EaseInOutSine
        )
    }

    val slideAnimationSpec: FiniteAnimationSpec<IntOffset> = remember {
        tween(
            durationMillis = 300,
            easing = EaseInOutSine
        )
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut(fadeAnimationSpec) + slideOutVertically(slideAnimationSpec) { it }
    ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
                .shadow(
                    shape = shape,
                    offset = DpOffset(0.dp, 0.dp),
                    radius = 12.dp
                )
                .background(colorTheme.colorBackground, shape)
                .noIndicationClickable(onClick = {})
                .navigationBarsPadding()
                .height(72.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            graphsWithStartRoutes.forEach { route ->
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()

                val isSelected = remember(route, currentTopRoute) { route == currentTopRoute }
                val iconResId = when (route) {
                    MainRoutes.RouteOne -> R.drawable.icon_home
                    MainRoutes.RouteTwo -> R.drawable.icon_map
                    MainRoutes.RouteThree -> R.drawable.icon_bar_chart
                    MainRoutes.RouteFour -> R.drawable.icon_account
                    else -> return@forEach
                }

                val scale by smartAnimateFloatAsState(
                    isActive = isPressed,
                    restValue = 1f,
                    activeValue = 0.85f,
                    restAnimationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    activeAnimationSpec = tween(
                        easing = EaseInOut,
                        durationMillis = 50
                    )
                )

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) colorTheme.colorGreenMain else Color.Transparent)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = ripple(color = colorTheme.colorButtonVector.copy(0.5f)),
                            onClick = {
                                if (!isSelected) {
                                    backStack.push(route)
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        imageVector = ImageVector.vectorResource(iconResId),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        colorFilter = if (isSelected) {
                            ColorFilter.tint(colorTheme.colorWhite)
                        } else {
                            ColorFilter.tint(colorTheme.colorTextDark)
                        }
                    )
                }
            }
        }
    }
}