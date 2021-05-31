package com.brunohensel.cardgame.home.domain.module

import androidx.annotation.DrawableRes

data class AvailableGame(
    val type: GameType,
    val name: String,
    @DrawableRes val icon: Int,
    val available: Boolean = false
)
