package com.example.maodou.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry

/**
 * 智能 Pop 退出动画
 * 根据滑动退出的方向自动计算缩放支点 (Pivot)
 *
 * @param targetScale 退出时的最终缩放比例
 * @param pivotBias 支点偏移量 (0.0 - 1.0)。
 * @param animationSpec 用于缩放(Scale)和透明度(Alpha)的动画配置 (Float类型)
 * @param offsetAnimationSpec 用于位移(Slide)的动画配置 (IntOffset类型)，默认与 animationSpec 的刚度保持一致
 */
fun AnimatedContentTransitionScope<NavBackStackEntry>.smartPopExitTransition(
    targetScale: Float = 0.9f,
    pivotBias: Float = 0.85f,
    animationSpec: FiniteAnimationSpec<Float> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = 0.001f
    ),
    offsetAnimationSpec: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    )
): ExitTransition {
    // 1. 判断方向
    val direction = getSlideDirection(this)

    // 2. 计算 Pivot (TransformOrigin)
    val pivotFractionX = if (direction == AnimatedContentTransitionScope.SlideDirection.End) {
        pivotBias
    } else {
        1f - pivotBias
    }

    // 3. 组合动画
    return scaleOut(
        targetScale = targetScale,
        transformOrigin = TransformOrigin(pivotFractionX, 0.5f),
        animationSpec = animationSpec // Scale 使用 Float Spec
    ) + fadeOut(
        animationSpec = animationSpec // Alpha 使用 Float Spec
    ) + slideOutHorizontally(
        targetOffsetX = { width ->
            if (direction == AnimatedContentTransitionScope.SlideDirection.End) width else -width
        },
        animationSpec = offsetAnimationSpec // Slide 必须使用 IntOffset Spec
    )
}

/**
 * 辅助函数：确定滑动方向
 * 默认策略：Pop 操作在 LTR 布局下默认为 "End" (向右)
 */
private fun getSlideDirection(
    scope: AnimatedContentTransitionScope<NavBackStackEntry>
): AnimatedContentTransitionScope.SlideDirection {

    val initialIndex = scope.initialState.destination.route
    val targetIndex = scope.targetState.destination.route

//    val initialDepth = scope.initialState.destination.hierarchy.count()
//    val targetDepth = scope.targetState.destination.hierarchy.count()

//    return if (targetIndex < initialIndex) {
//        // Pop：返回上一级 → 向右（End）
//        AnimatedContentTransitionScope.SlideDirection.End
//    } else {
        // Push：进入新页面 → 向左（Start）
        return AnimatedContentTransitionScope.SlideDirection.Start
//    }
}