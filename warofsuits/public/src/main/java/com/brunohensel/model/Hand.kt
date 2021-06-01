package com.brunohensel.model
import com.brunohensel.core.cardtypes.Card

/**
 * This class represents the hand that was just played.
 * @return a Pair of the winner and loser card.
 */
typealias Winner = Card
typealias Loser = Card
data class Hand(val playedHands: Pair<Winner, Loser>)
