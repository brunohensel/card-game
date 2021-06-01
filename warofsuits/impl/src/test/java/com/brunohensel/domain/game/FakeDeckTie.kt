package com.brunohensel.domain.game

import com.brunohensel.core.cardtypes.Card
import com.brunohensel.core.cardtypes.warofsuits.Ranks
import com.brunohensel.core.cardtypes.warofsuits.Suits
import com.brunohensel.datasource.local.WarOfSuitLocalDataSource

class FakeDeckTie : WarOfSuitLocalDataSource {

    override fun fetchDeckOfCards(): List<Card> {
        return  mutableListOf(
            //Player one deck
            Card(Ranks.NINE, Suits.HEARTS),
            Card(Ranks.THREE, Suits.CLUBS),
            //Player two deck
            Card(Ranks.EIGHT, Suits.DIAMONDS),
            Card(Ranks.KING, Suits.HEARTS)
        )
    }
}