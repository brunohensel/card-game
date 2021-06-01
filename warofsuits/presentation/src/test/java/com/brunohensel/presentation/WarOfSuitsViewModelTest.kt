package com.brunohensel.presentation

import com.brunohensel.domain.WarOfSuitsEvents
import com.brunohensel.domain.state.WarOfSuitsState
import com.brunohensel.domain.state.WarOfSuitsSyncState
import com.brunohensel.domain.state.WarOfSuitsSyncState.Started
import com.brunohensel.test.BaseUnitTest
import com.brunohensel.test.coroutines.test
import com.brunohensel.test.coroutines.testCoroutine
import org.junit.Test

internal class WarOfSuitsViewModelTest : BaseUnitTest<WarOfSuitsViewModel>() {
    private val fakeGame = FakeWarOfSuitsImpl()
    override fun sut(): WarOfSuitsViewModel = WarOfSuitsViewModel(fakeGame)

    @Test
    fun `emit WarOfSuitsState when start a new game, sync state should be Started`() =
        testCoroutine {
            val result = tested.state.test(this)
            val expected = WarOfSuitsState(players = Pair(fakeGame.playerOne, fakeGame.playerTwo),syncState = Started)
            result.assertFirst(expected).finish()
        }

    @Test
    fun `emit WarOfSuitsState for each new round`() = testCoroutine {
            fakeGame.shouldOneWin = true
            val result = tested.state.test(this)
            val expectedZero = WarOfSuitsState(
                players = Pair(fakeGame.playerOne, fakeGame.playerTwo),
                syncState = Started
            )
            val expectedOne =   WarOfSuitsState(
                players = Pair(fakeGame.playerOne, fakeGame.playerTwo),
                winner = fakeGame.playerOne,
                hand = fakeGame.handOne,
                syncState = WarOfSuitsSyncState.Round
            )
            tested.dispatch(WarOfSuitsEvents.PlayRound)
            fakeGame.shouldOneWin = false
            val expectedTwo =   WarOfSuitsState(
                players = Pair(fakeGame.playerOne, fakeGame.playerTwo),
                winner = fakeGame.playerTwo,
                hand = fakeGame.handTwo,
                syncState = WarOfSuitsSyncState.Round
            )
            tested.dispatch(WarOfSuitsEvents.PlayRound)

            result
                .assertValueAt(0, expectedZero)
                .assertValueAt(1, expectedOne)
                .assertValueAt(2, expectedTwo)
                .finish()
        }

    @Test
    fun `emit WarOfSuitsState when the game ends`() = testCoroutine {
        fakeGame.shouldEndGame = true
        val result = tested.state.test(this)
        tested.dispatch(WarOfSuitsEvents.PlayRound)
        val expected = WarOfSuitsState(winner = fakeGame.playerOne, syncState = WarOfSuitsSyncState.Finish)

        result.assertLast(expected).finish()
    }
}