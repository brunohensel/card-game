package com.brunohensel.datasource.local

import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.core.cardtypes.Card
import com.brunohensel.datasource.local.deck.Deck
import javax.inject.Inject

/**
 * Class responsible to fetch a deck of cards and returns it after the shuffled.
 */
@ApplicationScope
internal class WarOfSuitLocalDataSourceImpl @Inject constructor(private val deck: Deck) : WarOfSuitLocalDataSource {
    override fun fetchDeckOfCards(): List<Card> {
        return deck.cards.shuffled()
    }
}