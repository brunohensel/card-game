package com.brunohensel.model

import com.brunohensel.core.cardtypes.Card

/**
 * This class represents the hand that was just played.
 * @return a Pair of the winner and loser card.
 */
typealias PlayerOne = Card
typealias PlayerTwo = Card

data class Hand(
    val winner: Player?,
    val loser: Player,
    val playedHands: Pair<PlayerOne, PlayerTwo>,
    val playerOneScore: Int,
    val playerTwoScore: Int
)
