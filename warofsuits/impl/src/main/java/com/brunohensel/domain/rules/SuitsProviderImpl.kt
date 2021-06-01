package com.brunohensel.domain.rules

import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.core.cardtypes.warofsuits.Suits
import javax.inject.Inject

/**
 * Implements the [SuitsProvider] interface and returns a list of shuffled Suits for each new game.
 */
@ApplicationScope
internal class SuitsProviderImpl @Inject constructor(): SuitsProvider {
    override val shuffledSuits: List<Suits>
        get() = Suits.values().toList().shuffled()
}