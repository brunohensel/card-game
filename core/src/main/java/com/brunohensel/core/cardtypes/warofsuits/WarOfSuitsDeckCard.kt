package com.brunohensel.core.cardtypes.warofsuits

import com.brunohensel.core.R
import com.brunohensel.core.cardtypes.Card
import com.brunohensel.core.cardtypes.warofsuits.Ranks.ACE
import com.brunohensel.core.cardtypes.warofsuits.Suits.*

object WarOfSuitsDeckCard {

    var cards = mutableListOf<Card>()

    /**
     * Chose the for loop approach instead of the functional approach with for each because, for
     * this use case, it seemed to perform better. A naive measurement has shown for this particular
     * case that the for loop is about 4x faster.
     *
     *   measureNanoTime {
     *          for (suit in Suits.values())
     *             for (rank in Ranks.values())
     *                cards.add(Card(rank, suit, cardMapper(suit, rank)))
     *         }.also { println("BANCHMARK FOR LOOP: $it in nano") }
     */
    init {
        for (suit in Suits.values())
            for (rank in Ranks.values())
                cards.add(Card(rank, suit, cardMapper(suit, rank)))
    }

    private fun cardMapper(suits: BaseSuits, rank: BaseRank): Int {
        return when {
            suits == CLUBS && rank == Ranks.TWO -> R.drawable.ic_clubs_2
            suits == CLUBS && rank == Ranks.THREE -> R.drawable.ic_clubs_3
            suits == CLUBS && rank == Ranks.FOUR -> R.drawable.ic_clubs_4
            suits == CLUBS && rank == Ranks.FIVE -> R.drawable.ic_clubs_5
            suits == CLUBS && rank == Ranks.SIX -> R.drawable.ic_clubs_6
            suits == CLUBS && rank == Ranks.SEVEN -> R.drawable.ic_clubs_7
            suits == CLUBS && rank == Ranks.EIGHT -> R.drawable.ic_clubs_8
            suits == CLUBS && rank == Ranks.NINE -> R.drawable.ic_clubs_9
            suits == CLUBS && rank == Ranks.TEN -> R.drawable.ic_clubs_10
            suits == CLUBS && rank == Ranks.JACK -> R.drawable.ic_clubs_jack
            suits == CLUBS && rank == Ranks.QUEEN -> R.drawable.ic_clubs_queen
            suits == CLUBS && rank == Ranks.KING -> R.drawable.ic_clubs_king
            suits == CLUBS && rank == ACE -> R.drawable.ic_clubs_ace

            suits == DIAMONDS && rank == Ranks.TWO -> R.drawable.ic_diamonds_2
            suits == DIAMONDS && rank == Ranks.THREE -> R.drawable.ic_diamonds_3
            suits == DIAMONDS && rank == Ranks.FOUR -> R.drawable.ic_diamonds_4
            suits == DIAMONDS && rank == Ranks.FIVE -> R.drawable.ic_diamonds_5
            suits == DIAMONDS && rank == Ranks.SIX -> R.drawable.ic_diamonds_6
            suits == DIAMONDS && rank == Ranks.SEVEN -> R.drawable.ic_diamonds_7
            suits == DIAMONDS && rank == Ranks.EIGHT -> R.drawable.ic_diamonds_8
            suits == DIAMONDS && rank == Ranks.NINE -> R.drawable.ic_diamonds_9
            suits == DIAMONDS && rank == Ranks.TEN -> R.drawable.ic_diamonds_10
            suits == DIAMONDS && rank == Ranks.JACK -> R.drawable.ic_diamonds_jack
            suits == DIAMONDS && rank == Ranks.QUEEN -> R.drawable.ic_diamonds_queen
            suits == DIAMONDS && rank == Ranks.KING -> R.drawable.ic_diamonds_king
            suits == DIAMONDS && rank == ACE -> R.drawable.ic_diamonds_ace

            suits == HEARTS && rank == Ranks.TWO -> R.drawable.ic_hearts_2
            suits == HEARTS && rank == Ranks.THREE -> R.drawable.ic_hearts_3
            suits == HEARTS && rank == Ranks.FOUR -> R.drawable.ic_hearts_4
            suits == HEARTS && rank == Ranks.FIVE -> R.drawable.ic_hearts_5
            suits == HEARTS && rank == Ranks.SIX -> R.drawable.ic_hearts_6
            suits == HEARTS && rank == Ranks.SEVEN -> R.drawable.ic_hearts_7
            suits == HEARTS && rank == Ranks.EIGHT -> R.drawable.ic_hearts_8
            suits == HEARTS && rank == Ranks.NINE -> R.drawable.ic_hearts_9
            suits == HEARTS && rank == Ranks.TEN -> R.drawable.ic_hearts_10
            suits == HEARTS && rank == Ranks.JACK -> R.drawable.ic_hearts_jack
            suits == HEARTS && rank == Ranks.QUEEN -> R.drawable.ic_hearts_queen
            suits == HEARTS && rank == Ranks.KING -> R.drawable.ic_hearts_king
            suits == HEARTS && rank == ACE -> R.drawable.ic_hearts_ace

            suits == SPADES && rank == Ranks.TWO -> R.drawable.ic_spades_2
            suits == SPADES && rank == Ranks.THREE -> R.drawable.ic_spades_3
            suits == SPADES && rank == Ranks.FOUR -> R.drawable.ic_spades_4
            suits == SPADES && rank == Ranks.FIVE -> R.drawable.ic_spades_5
            suits == SPADES && rank == Ranks.SIX -> R.drawable.ic_spades_6
            suits == SPADES && rank == Ranks.SEVEN -> R.drawable.ic_spades_7
            suits == SPADES && rank == Ranks.EIGHT -> R.drawable.ic_spades_8
            suits == SPADES && rank == Ranks.NINE -> R.drawable.ic_spades_9
            suits == SPADES && rank == Ranks.TEN -> R.drawable.ic_spades_10
            suits == SPADES && rank == Ranks.JACK -> R.drawable.ic_spades_jack
            suits == SPADES && rank == Ranks.QUEEN -> R.drawable.ic_spades_queen
            suits == SPADES && rank == Ranks.KING -> R.drawable.ic_spades_king
            suits == SPADES && rank == ACE -> R.drawable.ic_spades_ace
            else -> throw IllegalArgumentException("Invalid card for the rank: $rank and suits: $suits")
        }
    }
}