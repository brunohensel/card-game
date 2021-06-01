package com.brunohensel.domain.rules

import com.brunohensel.core.cardtypes.warofsuits.Suits

/**
 * Public interface that provides, for each new game, a shuffled suits.
 *
 * I chose this approach because it makes easy to apply unit test by using a fake implementation.
 */
interface SuitsProvider {
    val  shuffledSuits: List<Suits>
}