package com.brunohensel.core.factory.deck

import com.brunohensel.core.cardtypes.Card

/**
 * Factory function to provide a dynamic way to create a deque of cards based on its type
 */
interface DeckFactory {
    fun createDeckOfCards(type: CardSetType): List<Card>
}

enum class CardSetType {
WAR_OF_SUITS, POKER, BLACKJACK
}

