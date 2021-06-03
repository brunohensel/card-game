package com.brunohensel.domain.game

import com.brunohensel.WarOfSuits
import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.core.cardtypes.Card
import com.brunohensel.core.cardtypes.warofsuits.Suits
import com.brunohensel.core.getOrNull
import com.brunohensel.datasource.local.WarOfSuitLocalDataSource
import com.brunohensel.domain.rules.SuitsProvider
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
    private val suitsProvider: SuitsProvider,
    private val gameRule: WarOfSuitsRules
) : WarOfSuits {

    override fun getPlayers(): Pair<Player, Player> {
        return Pair(playerOne, playerTwo)
    }

    override fun start() {
        clearPreviousDeck()
        gameRule.shuffleSuits()
        with(localSource.fetchDeckOfCards()) {
            val (playerOneDeque, playerTwoDeque) = chunked(this.size / 2)
            playerOne.regularPile.cards.addAll(playerOneDeque)
            playerTwo.regularPile.cards.addAll(playerTwoDeque)
        }
    }

    private fun clearPreviousDeck() {
        playerOne.regularPile.cards.clear()
        playerTwo.regularPile.cards.clear()
        playerOne.discardPile.cards.clear()
        playerTwo.discardPile.cards.clear()
        playerOne.layDownCard = null
        playerTwo.layDownCard = null
    }

    override fun playRound(): Round {
        playerOne.layDownCard = playerOne.regularPile.cards.pollLast()
        playerTwo.layDownCard = playerTwo.regularPile.cards.pollLast()
        val cardPlayerOne = playerOne.layDownCard
        val cardPlayerTwo = playerTwo.layDownCard

        if (cardPlayerOne != null && cardPlayerTwo != null) {
            val winnerCard = gameRule.validateRound(cardPlayerOne, cardPlayerTwo)
            return if (winnerCard == cardPlayerOne) {
                playerOne.discardPile.cards.addCardToDiscardPile(cardPlayerOne, cardPlayerTwo)
                Round.Played(
                    playerOne.regularPile.cards.size,
                    Hand(
                        winner = playerOne.name,
                        loser = playerTwo.name,
                        playedHands = Pair(cardPlayerOne, cardPlayerTwo),
                        playerOneScore = playerOne.discardPile.cards.size / 2,
                        playerTwoScore = playerTwo.discardPile.cards.size / 2
                    )
                )
            } else {
                playerTwo.discardPile.cards.addCardToDiscardPile(cardPlayerOne, cardPlayerTwo)
                Round.Played(
                    playerOne.regularPile.cards.size,
                    Hand(
                        winner = playerTwo.name,
                        loser = playerOne.name,
                        playedHands = Pair(cardPlayerOne, cardPlayerTwo),
                        playerOneScore = playerOne.discardPile.cards.size / 2,
                        playerTwoScore = playerTwo.discardPile.cards.size / 2
                    )
                )
            }

        } else {
            return findGameWinner()
        }
    }

    override fun restartGame(): Round {
        clearPreviousDeck()
        return Round.Restarted
    }

    override fun fetchShuffledSuits(): List<Suits> {
       return suitsProvider.shuffledSuits
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