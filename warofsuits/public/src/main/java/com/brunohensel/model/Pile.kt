package com.brunohensel.model

import com.brunohensel.core.cardtypes.Card
import java.util.*

/**
 * Represents a Pile os cards, which each player has 2 of them
 * @param type indicates whether it is a discard or regular pile
 * @param cards represents a deque of cards.
 *
 * I chose to use the [Deque] because it prevents the access in the middle of the deque, using the
 * index, i.e, cards[0]. It acts like a stack - LIFO - but the Stack data structure extends Vector,
 * which makes possible random access to the elements through its index.
 */
data class Pile(val type: PileType, val cards: Deque<Card> = LinkedList())

enum class PileType{
    REGULAR, DISCARD
}