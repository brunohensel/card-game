package com.brunohensel.cardgame.home.datasource.remote

import com.brunohensel.cardgame.R
import com.brunohensel.cardgame.home.domain.module.AvailableGame
import com.brunohensel.cardgame.home.domain.module.GameType

/**
 * A singleton Object that will create a list of AvailableGame, representing a response
 * coming from an api call.
 */
object AvailableGamesList {
    operator fun invoke() = listOf(
        AvailableGame(GameType.WAR_OF_SUITS, "War of Suits", R.drawable.ic_available_game, true),
        AvailableGame(GameType.POKER, "Poker", R.drawable.ic_available_game, false),
        AvailableGame(GameType.BLACKJACK, "Blackjack", R.drawable.ic_available_game, false)
    )
}