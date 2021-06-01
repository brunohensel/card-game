package com.brunohensel.domain.game

import com.brunohensel.WarOfSuits
import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.core.cardtypes.Card
import com.brunohensel.core.getOrNull
import com.brunohensel.datasource.local.WarOfSuitLocalDataSource
import com.brunohensel.domain.rules.WarOfSuitsRules
import com.brunohensel.model.Hand
import com.brunohensel.model.Player
import com.brunohensel.model.Round
import com.brunohensel.model.Round.Finished
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * This class implements [WarOfSuits] interface
 * @param localSource that is responsible to fetch a deck of cards
 * @param playerOne is a pre-defined player
 * @param playerTwo is a pre-defined player
 * @param gameRule are the rules of the game, this class will return who has won the round and game
 */
@ApplicationScope
internal class WarOfSuitImpl @Inject constructor(
    private val localSource: WarOfSuitLocalDataSource,
    @Named("Player1") private val playerOne: Player,
    @Named("Player2") private val playerTwo: Player,
    private val gameRule: WarOfSuitsRules
) : WarOfSuits {

    override fun getPlayers(): Pair<Player, Player> {
        return Pair(playerOne, playerTwo)
    }

    override fun start() {
        with(localSource.fetchDeckOfCards()) {
            val (playerOneDeque, playerTwoDeque) = chunked(this.size / 2)
            playerOne.regularPile.cards.addAll(playerOneDeque)
            playerTwo.regularPile.cards.addAll(playerTwoDeque)
        }
    }

    override fun playRound(): Round {
        playerOne.layDownCard = playerOne.regularPile.cards.pollLast()
        playerTwo.layDownCard = playerTwo.regularPile.cards.pollLast()
        val cardPlayerOne = playerOne.layDownCard
        val cardPlayerTwo = playerTwo.layDownCard

        if (cardPlayerOne != null && cardPlayerTwo != null) {
            val winnerCard = gameRule.validateRound(cardPlayerOne, cardPlayerTwo)
            return if (winnerCard == cardPlayerOne) {
                playerOne.discardPile.cards.addCardToDiscardPile(
                    cardPlayerOne,
                    cardPlayerTwo
                )
                Round.RoundWinner(
                    playerOne, Pair(playerOne, playerTwo),
                    Hand(Pair(winnerCard, cardPlayerTwo))
                )
            } else {
                playerTwo.discardPile.cards.addCardToDiscardPile(
                    cardPlayerOne,
                    cardPlayerTwo
                )
                Round.RoundWinner(
                    playerTwo, Pair(playerOne, playerTwo),
                    Hand(Pair(winnerCard, cardPlayerOne))
                )
            }

        } else {
            return findGameWinner()
        }
    }

    private fun findGameWinner(): Finished {
        val winnerDeque = gameRule.defineGameWinner(playerOne.discardPile.cards, playerTwo.discardPile.cards)
       return winnerDeque
            .getOrNull()
            ?.let { if (it == playerOne.discardPile.cards) Finished(playerOne) else Finished(playerTwo) }
            ?: Finished(isTied = true)
    }

    private fun Deque<Card>.addCardToDiscardPile(vararg card: Card) {
        card.forEach { addLast(it) }
    }
}