package com.brunohensel.domain.rules

import com.brunohensel.core.Option
import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.core.cardtypes.Card
import com.brunohensel.core.cardtypes.warofsuits.Ranks
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that implements the Rule interface and encapsulates the rules that will be applied to
 * figure out which player has won the game.
 * @param suitsPriorityProvider which is responsible to shuffled the suits order before each game.
 */
@ApplicationScope
internal class WarOfSuitsRules @Inject constructor(private val suitsPriorityProvider: SuitsProvider) {

    /**
     * Function that applies the defined rules to find the winner.
     * @param playerOneCard represents the played card from player one
     * @param playerTwoCard represents the played card from player two
     * @return [Card] which represents the winner card
     */
    fun validateRound(playerOneCard: Card, playerTwoCard: Card): Card {

       val playerOneRank =  playerOneCard.rank as Ranks
        val playerTwoRank =  playerTwoCard.rank as Ranks

        return when {
            playerOneRank.ordinal > playerTwoRank.ordinal -> {
                playerOneCard
            }
            playerOneRank.ordinal < playerTwoRank.ordinal  -> {
                playerTwoCard
            }
            else -> {
                var suitOne = 0
                var suitTwo = 0
                suitsPriorityProvider.shuffledSuits.asSequence().forEachIndexed { position, suits ->
                    if (playerOneCard.suit == suits) suitOne = position
                    if (playerTwoCard.suit == suits) suitTwo = position
                }
                if (suitOne > suitTwo) playerOneCard else playerTwoCard
            }
        }
    }

    /**
     * Checks which player has the bigger discard pile, this player is the winner of the game.
     * @return [Option] that or contains the winner in the Some<> data constructor or it will be
     * None, which indicates that the game has tied.
     */
    fun defineGameWinner(
        playerOneDiscard: Deque<Card>,
        playerTwoDiscard: Deque<Card>
    ): Option<Deque<Card>> =
        when{
            playerOneDiscard.size > playerTwoDiscard.size -> Option.Some(playerOneDiscard)
            playerOneDiscard.size < playerTwoDiscard.size -> Option.Some(playerTwoDiscard)
            else -> Option.None
        }
}
