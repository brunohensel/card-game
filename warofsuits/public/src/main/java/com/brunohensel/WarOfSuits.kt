package com.brunohensel

import com.brunohensel.model.Player
import com.brunohensel.model.Round

/**
 * Public interface that represents the core of the "war of suits" game. It exposes public methods,
 * that will be used to play the game.
 */
interface WarOfSuits {
    /**
     * Returns a Pair with 2 players that are about to start the round.
     */
    fun getPlayers(): Pair<Player, Player>

    /**
     * It starts the game getting the deck of cards already shuffled for this specific game, split it
     * into 2 piles of equal size, and set each of them to a player.
     */
    fun start()

    /**
     * It plays a new round every time it is invoked.
     * @return a Round object that contains all the information about that round that is just played,
     * such as, winner, players, and hands.
     */
    fun playRound(): Round

    /**
     * Allows the players to start a new game. It will set all players deque as empty
     */
    fun restartGame(): Round
}