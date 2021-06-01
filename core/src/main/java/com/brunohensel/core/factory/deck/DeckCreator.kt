package com.brunohensel.core.factory.deck

import com.brunohensel.core.cardtypes.Card
import com.brunohensel.core.cardtypes.warofsuits.WarOfSuitsDeckCard
/**
 * This class implements the [DeckFactory] and returns a list of cards that will be later on added
 * into a deque os cards.
 */
class DeckCreator: DeckFactory {
    override fun createDeckOfCards(type: CardSetType): List<Card> {
        return when(type){
            CardSetType.POKER -> WarOfSuitsDeckCard.cards
            CardSetType.BLACKJACK -> WarOfSuitsDeckCard.cards
            CardSetType.WAR_OF_SUITS -> WarOfSuitsDeckCard.cards
        }
    }
}