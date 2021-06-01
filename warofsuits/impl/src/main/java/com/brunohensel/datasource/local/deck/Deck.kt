package com.brunohensel.datasource.local.deck
import com.brunohensel.core.cardtypes.Card
/**
 * Define the behavior os a deck of cards.
 */
interface Deck {
    /**
     * Creates and holds a list of Cards
     */
    var cards: MutableList<Card>
}