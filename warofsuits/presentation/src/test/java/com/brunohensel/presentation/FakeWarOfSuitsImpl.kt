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
    val handOne = Hand(playerOne.name, playerTwo.name, Pair(Card(Ranks.KING, Suits.HEARTS), Card(Ranks.THREE, Suits.CLUBS)),10, 2)
    val handTwo = Hand(playerTwo.name, playerOne.name, Pair(Card(Ranks.NINE, Suits.HEARTS), Card(Ranks.EIGHT, Suits.DIAMONDS)),2, 10)

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
        playerOne.regularPile.cards.pollLast()
        playerTwo.regularPile.cards.pollLast()
      return if (shouldEndGame){
          Round.Finished(playerOne)
      }else {
          if (shouldOneWin) {
              playerOne.discardPile.cards.add(Card(Ranks.KING, Suits.HEARTS))
              playerOne.discardPile.cards.add(Card(Ranks.THREE, Suits.CLUBS))
              Round.Played(playerOne.regularPile.cards.size, handOne)
          } else {
              playerTwo.discardPile.cards.add(Card(Ranks.NINE, Suits.HEARTS))
              playerTwo.discardPile.cards.add(Card(Ranks.EIGHT, Suits.DIAMONDS))
              Round.Played(playerOne.regularPile.cards.size, handTwo)
          }
      }
    }

    override fun restartGame(): Round {
       return Round.Restarted
    }

    override fun fetchShuffledSuits(): List<Suits> {
        return Suits.values().toList()
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