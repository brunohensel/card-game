package com.brunohensel.domain.game

import com.brunohensel.core.cardtypes.warofsuits.Suits
import com.brunohensel.domain.rules.SuitsProvider

class FakeSuitsProvider : SuitsProvider {
    override val shuffledSuits: List<Suits>
        get() = Suits.values().toList()
}