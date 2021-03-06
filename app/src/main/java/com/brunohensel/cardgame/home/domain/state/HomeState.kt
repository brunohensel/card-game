package com.brunohensel.cardgame.home.domain.state

import com.brunohensel.cardgame.home.domain.model.AvailableGame
/**
 * This class represents the state of the UI,
 * it contains an object that will be displayed in case of Success and an throwable in case of
 * Error.
 */
data class HomeState(
    val availableGames: List<AvailableGame> = emptyList(),
    val failure: Throwable? = null,
    val syncState: HomeSyncState = HomeSyncState.Idle
)
