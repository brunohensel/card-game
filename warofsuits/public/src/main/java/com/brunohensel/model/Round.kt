package com.brunohensel.model

/**
 * Represents the Round of a game
 * The Round can result in a winner or in a finish event
 */
sealed class Round {
    data class Played(val remainingRound: Int, val hand: Hand) : Round()
    data class Finished(val winner: Player? = null, val isTied: Boolean = false) : Round()
    object Restarted: Round()
}
