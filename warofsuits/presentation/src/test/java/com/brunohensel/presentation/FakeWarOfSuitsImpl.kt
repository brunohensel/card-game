package com.brunohensel.presentation

import com.brunohensel.WarOfSuits
import com.brunohensel.core.cardtypes.Card
import com.brunohensel.core.cardtypes.warofsuits.Ranks
import com.brunohensel.core.cardtypes.warofsuits.Suits
import com.brunohensel.model.Hand
import com.brunohensel.model.Player
import com.brunohensel.model.Round

class FakeWarOfSuitsImpl : WarOfSuits {

    val playerOne = Player(name = "Player one")
    val playerTwo = Player(name = "Player two")
    val handOne = Hand(Pair(Card(Ranks.KING, Suits.HEARTS), Card(Ranks.THREE, Suits.CLUBS)))
    val handTwo = Hand(Pair(Card(Ranks.NINE, Suits.HEARTS), Card(Ranks.EIGHT, Suits.DIAMONDS)))

    var shouldOneWin = false
    var shouldEndGame = false

    override fun getPlayers(): Pair<Player, Player> {
        return Pair(playerOne, playerTwo)
    }

    override fun start() {
        playerOne.regularPile.cards.addAll(deck1)
        playerTwo.regularPile.cards.addAll(deck2)
    }

    override fun playRound(): Round {

      return if (shouldEndGame){
          Round.Finished(playerOne)
      }else {
          if (shouldOneWin) {
              playerOne.discardPile.cards.add(Card(Ranks.KING, Suits.HEARTS))
              playerOne.discardPile.cards.add(Card(Ranks.THREE, Suits.CLUBS))
              Round.RoundWinner(
                  playerOne, Pair(playerOne, playerTwo),
                  Hand(Pair(Card(Ranks.KING, Suits.HEARTS), Card(Ranks.THREE, Suits.CLUBS)))
              )
          } else {
              playerTwo.discardPile.cards.add(Card(Ranks.NINE, Suits.HEARTS))
              playerTwo.discardPile.cards.add(Card(Ranks.EIGHT, Suits.DIAMONDS))
              Round.RoundWinner(
                  playerTwo, Pair(playerOne, playerTwo),
                  Hand(Pair(Card(Ranks.NINE, Suits.HEARTS), Card(Ranks.EIGHT, Suits.DIAMONDS)))
              )
          }
      }
    }

    val deck1 = mutableListOf(
        //Player one deck
        Card(Ranks.ACE, Suits.CLUBS),
        Card(Ranks.THREE, Suits.SPADES),
        Card(Ranks.NINE, Suits.HEARTS),
        Card(Ranks.THREE, Suits.CLUBS)
    )

    val deck2 = mutableListOf(
        //Player two deck
        Card(Ranks.ACE, Suits.HEARTS),
        Card(Ranks.NINE, Suits.SPADES),
        Card(Ranks.EIGHT, Suits.DIAMONDS),
        Card(Ranks.KING, Suits.HEARTS)
    )
}