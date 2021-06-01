package com.brunohensel.model

/**
 * Represents the Round of a game
 * The Round can result in a winner or in a finish event
 */
sealed class Round {
    data class RoundWinner(val winner: Player, val players: Pair<Player, Player>, val hand: Hand): Round()
    data class Finished(val winner: Player) : Round()
}
