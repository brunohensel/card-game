package com.brunohensel.datasource.local.deck

import com.brunohensel.core.annotations.ActivityScope
import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.core.factory.deck.CardSetType
import com.brunohensel.core.factory.deck.DeckCreator
import javax.inject.Inject

/**
 * Implementation of a [Deck]
 * It composes the [DeckCreator] in order to access the factory method createDeckOfCards, and passes
 * the type of a card as argument to it to get a deck of a card from this type.
 */
@ApplicationScope
internal class DeckImpl @Inject constructor(): Deck {
    private val factory = DeckCreator()
    /**
     * It calls the factory method to obtain a deck of card from the [CardSetType] passed in as
     * argument.
     */
    override var cards = factory.createDeckOfCards(CardSetType.POKER).toMutableList()
}