package com.brunohensel.domain.game

import com.brunohensel.WarOfSuits
import com.brunohensel.datasource.local.WarOfSuitLocalDataSource
import com.brunohensel.datasource.local.WarOfSuitLocalDataSourceImpl
import com.brunohensel.datasource.local.deck.Deck
import com.brunohensel.datasource.local.deck.DeckImpl
import com.brunohensel.domain.rules.SuitsProvider
import com.brunohensel.domain.rules.SuitsProviderImpl
import com.brunohensel.domain.rules.WarOfSuitsRules
import com.brunohensel.model.Player
import com.brunohensel.model.Round
import com.brunohensel.test.BaseUnitTest
import org.junit.Test

internal class WarOfSuitImplTest : BaseUnitTest<WarOfSuitImpl>(){
    private val deck: Deck = DeckImpl()
    private val suitsProvider: SuitsProvider = SuitsProviderImpl()
    private val dataSource: WarOfSuitLocalDataSource = WarOfSuitLocalDataSourceImpl(deck)
    private val playerOne = Player(name = "playerOne")
    private val playerTwo = Player(name = "playerTwo")
    override fun sut(): WarOfSuitImpl = WarOfSuitImpl(dataSource, playerOne, playerTwo, WarOfSuitsRules(suitsProvider))

    @Test
    fun `The whole set must be split into two piles of equal size and each player gets one`() {
        val playerOneDeque = playerOne.regularPile.cards
        val playerTwoDeque = playerTwo.regularPile.cards
        assert(playerOneDeque.isEmpty() && playerTwoDeque.isEmpty())
        tested.start()
        assert(playerOneDeque.isNotEmpty() && playerTwoDeque.isNotEmpty())
        assert(playerOneDeque.size == playerTwoDeque.size)
        assert(playerOneDeque.size == dataSource.fetchDeckOfCards().size / 2)
    }

    @Test
    fun `Players have access to (and can only play) the top card of their regular pile`() {
        tested.start()
        val previousLastPlayerOneCard = playerOne.regularPile.cards.last
        val previousLastPlayerTwoCard = playerTwo.regularPile.cards.last
        tested.playRound()
        val currentLastPlayerOneCard = playerOne.regularPile.cards.last
        val currentLastPlayerTwoCard = playerTwo.regularPile.cards.last
        assert(previousLastPlayerOneCard != currentLastPlayerOneCard)
        assert(previousLastPlayerTwoCard != currentLastPlayerTwoCard)
    }

    @Test
    fun `In each round, both players lay down a card`() {
        tested.start()
        val playerOneDequeSizeStart = playerOne.regularPile.cards.size
        val playerTwoDequeSizeStart = playerTwo.regularPile.cards.size
        assert(playerOneDequeSizeStart + playerTwoDequeSizeStart == dataSource.fetchDeckOfCards().size)

        tested.playRound()
        val playerOneDequeSizeRoundOne = playerOne.regularPile.cards.size
        val playerTwoDequeSizeRoundOne = playerTwo.regularPile.cards.size
        assert(playerOneDequeSizeRoundOne == playerOneDequeSizeStart - 1)
        assert(playerTwoDequeSizeRoundOne == playerOneDequeSizeStart - 1)
    }

    @Test
    fun `The player holding the highest value card wins the round`() {
        val fakeSuitsProvider: SuitsProvider = FakeSuitsProvider()
        val game: WarOfSuits = WarOfSuitImpl(FakeDeck(), playerOne, playerTwo, WarOfSuitsRules(fakeSuitsProvider))
        game.start()
        val resultRoundOne = game.playRound()
        assert(resultRoundOne is Round.Played && resultRoundOne.hand.winner == playerTwo )

        val resultRoundTwo = game.playRound()
        assert(resultRoundTwo is Round.Played && resultRoundTwo.hand.winner == playerOne)

        val resultRoundThree = game.playRound()
        assert(resultRoundThree is Round.Played && resultRoundThree.hand.winner == playerTwo)

        //In case that both cards have equal value, the winner card is decided based on their suits
        val resultRoundFour = game.playRound()
        assert(resultRoundFour is Round.Played && resultRoundFour.hand.winner == playerTwo)
    }

    @Test
    fun `The winner puts both cards into their discard pile`() {
        val fakeSuitsProvider: SuitsProvider = FakeSuitsProvider()
        val game: WarOfSuits = WarOfSuitImpl(FakeDeck(), playerOne, playerTwo, WarOfSuitsRules(fakeSuitsProvider))
        game.start()
        assert(playerOne.discardPile.cards.isEmpty())
        assert(playerTwo.discardPile.cards.isEmpty())

        game.playRound()
        assert(playerTwo.discardPile.cards.isNotEmpty())
        assert(playerTwo.discardPile.cards.size == 2)
        game.playRound()
        assert(playerOne.discardPile.cards.isNotEmpty())
        assert(playerOne.discardPile.cards.size == 2)
    }

    @Test
    fun `The game finishes when both players have played all of their cards`() {
        val fakeSuitsProvider: SuitsProvider = FakeSuitsProvider()
        val game: WarOfSuits = WarOfSuitImpl(FakeDeck(), playerOne, playerTwo, WarOfSuitsRules(fakeSuitsProvider))
        game.start()
        val playerOneRegularDeque = playerOne.regularPile.cards
        val playerTwoRegularDeque = playerTwo.regularPile.cards
        while (playerOneRegularDeque.isNotEmpty() && playerTwoRegularDeque.isNotEmpty()){ game.playRound() }
        val round: Round = game.playRound()
        assert(playerOneRegularDeque.isEmpty() && playerTwoRegularDeque.isEmpty())
        assert(round == Round.Finished(playerTwo))
    }

    @Test
    fun `The player with more cards in their discard pile wins the game`(){
        val fakeSuitsProvider: SuitsProvider = FakeSuitsProvider()
        val game: WarOfSuits = WarOfSuitImpl(FakeDeck(), playerOne, playerTwo, WarOfSuitsRules(fakeSuitsProvider))
        game.start()
        val playerOneRegularDeque = playerOne.regularPile.cards
        val playerTwoRegularDeque = playerTwo.regularPile.cards
        while (playerOneRegularDeque.isNotEmpty() && playerTwoRegularDeque.isNotEmpty()){ game.playRound() }
        val playerOneDiscardDeque = playerOne.discardPile.cards
        val playerTwoDiscardDeque = playerTwo.discardPile.cards
        assert(playerOneDiscardDeque.size < playerTwoDiscardDeque.size)
        val result = game.playRound()
        assert(result == Round.Finished(playerTwo))
    }

    @Test
    fun `The game will tie when both player have the same amount in the discard pile`(){
        val fakeSuitsProvider: SuitsProvider = FakeSuitsProvider()
        val game: WarOfSuits = WarOfSuitImpl(FakeDeckTie(), playerOne, playerTwo, WarOfSuitsRules(fakeSuitsProvider))
        game.start()
        val playerOneRegularDeque = playerOne.regularPile.cards
        val playerTwoRegularDeque = playerTwo.regularPile.cards
        while (playerOneRegularDeque.isNotEmpty() && playerTwoRegularDeque.isNotEmpty()){ game.playRound() }
        val playerOneDiscardDeque = playerOne.discardPile.cards
        val playerTwoDiscardDeque = playerTwo.discardPile.cards
        assert(playerOneDiscardDeque.size == playerTwoDiscardDeque.size)
        val result = game.playRound()
        assert(result == Round.Finished(isTied = true))
    }

    @Test
    fun `All players deque will be set as empty when the game is restarted`(){
        val fakeSuitsProvider: SuitsProvider = FakeSuitsProvider()
        val tested: WarOfSuits = WarOfSuitImpl(FakeDeck(), playerOne, playerTwo, WarOfSuitsRules(fakeSuitsProvider))
        val playerOneDeque = playerOne.regularPile.cards
        val playerTwoDeque = playerTwo.regularPile.cards
        val playerTwoDiscardDeque = playerTwo.discardPile.cards
        tested.start()
        assert(playerOneDeque.isNotEmpty() && playerTwoDeque.isNotEmpty())

        tested.playRound()
        assert(playerTwoDiscardDeque.isNotEmpty())

        tested.restartGame()
        assert(playerOneDeque.isEmpty() && playerTwoDeque.isEmpty() && playerTwoDiscardDeque.isEmpty())
    }
}