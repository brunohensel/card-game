package com.brunohensel.presentation

import com.brunohensel.domain.WarOfSuitsEvents
import com.brunohensel.domain.WarOfSuitsSingleEvents
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
            val expected = WarOfSuitsState(
                players = Pair(fakeGame.playerOne, fakeGame.playerTwo),
                syncState = Started)
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
                hand = fakeGame.handOne,
                rounds = 3,
                syncState = WarOfSuitsSyncState.Round
            )
            tested.dispatch(WarOfSuitsEvents.PlayRound)
            fakeGame.shouldOneWin = false
            val expectedTwo =   WarOfSuitsState(
                players = Pair(fakeGame.playerOne, fakeGame.playerTwo),
                hand = fakeGame.handTwo,
                rounds = 2,
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
        val result = tested.state.test(this)
        fakeGame.shouldOneWin = true
        tested.dispatch(WarOfSuitsEvents.PlayRound)
        fakeGame.shouldEndGame = true
        tested.dispatch(WarOfSuitsEvents.PlayRound)
        val expected = WarOfSuitsState(Pair(fakeGame.playerOne, fakeGame.playerTwo),fakeGame.handOne, syncState = WarOfSuitsSyncState.Finish)
        result.assertLast(expected).finish()
    }

    @Test
    fun `emit WarOfSuitsState when the game is restarted`() = testCoroutine {
        val result = tested.state.test(this)
        fakeGame.shouldOneWin = true
        tested.dispatch(WarOfSuitsEvents.PlayRound)
        fakeGame.shouldOneWin = false
        tested.dispatch(WarOfSuitsEvents.PlayRound)
        tested.dispatch(WarOfSuitsEvents.Restart)
        val expected = WarOfSuitsState(Pair(fakeGame.playerOne, fakeGame.playerTwo), syncState = WarOfSuitsSyncState.Restarted)
        result.assertLast(expected).finish()
    }

    @Test
    fun `emit WarOfSuitsSingleEvents for fetch History`() = testCoroutine {
        val resultOneShot = tested.oneShotEvent.test(this)
        val resultState = tested.state.test(this)
        tested.dispatch(WarOfSuitsEvents.History)
        val expectedState =  WarOfSuitsState(Pair(fakeGame.playerOne, fakeGame.playerTwo), syncState = WarOfSuitsSyncState.Idle)
        val expectedSingleEvent = WarOfSuitsSingleEvents.History(emptyList())
        resultState.assertLast(expectedState).finish()
        resultOneShot.assertLast(expectedSingleEvent).finish()
    }
}