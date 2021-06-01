package com.brunohensel.model

import com.brunohensel.core.cardtypes.Card
import java.util.*

/**
 * This models the player object.
 * @param layDownCard are the cards the are shown each round
 * @param regularPile is the pile that each player gets at the beginning of the game
 * @param discardPile is the pile that each winner player gets after each round.
 */
data class Player(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    var layDownCard: Card? = null,
    val regularPile: Pile = Pile(PileType.REGULAR),
    val discardPile: Pile = Pile(PileType.DISCARD)
)
