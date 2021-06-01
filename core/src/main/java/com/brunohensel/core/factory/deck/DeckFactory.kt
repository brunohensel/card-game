package com.brunohensel.core.factory.deck

import com.brunohensel.core.cardtypes.Card

interface DeckFactory {
    fun createDeckOfCards(type: CardSetType): List<Card>
}

enum class CardSetType {
WAR_OF_SUITS, POKER, BLACKJACK
}

