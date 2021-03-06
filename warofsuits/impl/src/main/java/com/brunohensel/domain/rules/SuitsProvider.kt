package com.brunohensel.domain.rules

import com.brunohensel.core.cardtypes.warofsuits.Suits

/**
 * Public interface that provides, for each new game, a shuffled suits.
 *
 * I chose this approach because it makes easy to apply unit test by using a fake implementation.
 */
interface SuitsProvider {
    /**
     * get the shuffled suits for a game
     */
    val  shuffledSuits: List<Suits>

    /**
     * Helper function to shuffle the suits if the player restarts the game.
     */
    fun shuffleSuits()
}