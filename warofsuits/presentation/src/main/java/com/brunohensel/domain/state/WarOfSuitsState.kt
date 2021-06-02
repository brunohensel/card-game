package com.brunohensel.domain.state

import com.brunohensel.model.Hand
import com.brunohensel.model.Player

data class WarOfSuitsState(
    val players: Pair<Player, Player>? = null,
    val hand: Hand? = null,
    val syncState: WarOfSuitsSyncState = WarOfSuitsSyncState.Idle
)

sealed class WarOfSuitsSyncState {
    object Idle : WarOfSuitsSyncState()
    object Started : WarOfSuitsSyncState()
    object Round : WarOfSuitsSyncState()
    object Finish : WarOfSuitsSyncState()
}
