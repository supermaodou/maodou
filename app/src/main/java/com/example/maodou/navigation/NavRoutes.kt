package com.example.maodou.navigation

object NavRoutes {
    const val HOME_ROUTE = "home"
    const val FAVORITES_ROUTE = "favorites"
    const val PROFILE_ROUTE = "profile"

    const val SETTINGS_ROUTE = "settings"

    const val DETAIL_ROUTE = "detail/{itemNumber}"  // 新增详情页面路由
    fun detailRoute(itemNumber: Int) = "detail/$itemNumber"  // 路由生成函数
}