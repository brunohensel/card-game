package com.brunohensel.datasource.local

import com.brunohensel.datasource.local.deck.Deck
import com.brunohensel.datasource.local.deck.DeckImpl
import com.brunohensel.test.BaseUnitTest
import org.junit.Test

internal class WarOfSuitLocalDataSourceImplTest : BaseUnitTest<WarOfSuitLocalDataSourceImpl>() {
    private val deck: Deck = DeckImpl()
    override fun sut(): WarOfSuitLocalDataSourceImpl = WarOfSuitLocalDataSourceImpl(deck)

    @Test
    fun `The set must be shuffled at the beginning of each game`() {
        val previousSetOfCard = tested.fetchDeckOfCards()
        val actualSetOfCards = tested.fetchDeckOfCards()
        assert(previousSetOfCard != actualSetOfCards)
    }
}