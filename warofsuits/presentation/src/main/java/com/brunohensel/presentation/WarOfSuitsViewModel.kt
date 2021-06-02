package com.brunohensel.presentation

import com.brunohensel.WarOfSuits
import com.brunohensel.core.annotations.ActivityScope
import com.brunohensel.core.base.viewmodel.BaseStateViewModel
import com.brunohensel.domain.WarOfSuitsEvents
import com.brunohensel.domain.WarOfSuitsEvents.*
import com.brunohensel.domain.WarOfSuitsSingleEvents
import com.brunohensel.domain.state.WarOfSuitsState
import com.brunohensel.domain.state.WarOfSuitsSyncState
import com.brunohensel.model.Hand
import com.brunohensel.model.Round
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ActivityScope
class WarOfSuitsViewModel @Inject constructor(
    private val game: WarOfSuits
) : BaseStateViewModel<WarOfSuitsState, WarOfSuitsEvents>(
    initialState = WarOfSuitsState(), initialEvent = Start
) {
    private val eventChannel = Channel<WarOfSuitsSingleEvents>()

    /**
     * The [eventChannel] is used to send single shot events that should not be replayed in case of
     * rotation of the screen - Behavior of the [StateFlow].
     *
     * [oneShotEvent] is a hot flow that emits one single flow at a time and don't repeat the last
     * emission.
     */
    val oneShotEvent: Flow<WarOfSuitsSingleEvents> = eventChannel.receiveAsFlow()

    private val inMemoryHistory: MutableList<Hand?> = mutableListOf()

    override fun process(event: WarOfSuitsEvents): Flow<WarOfSuitsState> {
        return when (event) {
            PlayRound -> playRound()
            Start -> startGame()
            Restart -> restartGame()
            History -> fetchGameHistory()
        }
    }

    private fun restartGame(): Flow<WarOfSuitsState> = flow {
        game.restartGame()
        game.start()
        inMemoryHistory.clear()
        emit(WarOfSuitsState(game.getPlayers(), syncState = WarOfSuitsSyncState.Restarted))
    }

    private fun fetchGameHistory(): Flow<WarOfSuitsState> = flow {
        eventChannel.send(inMemoryHistory.filterNotNull().run { WarOfSuitsSingleEvents.History(history = this)})
        emit(state.value.copy(syncState = WarOfSuitsSyncState.Idle))
    }

    private fun startGame(): Flow<WarOfSuitsState> = flow {
        game.start()
        emit(WarOfSuitsState(game.getPlayers(), syncState = WarOfSuitsSyncState.Started))
    }

    private fun playRound(): Flow<WarOfSuitsState> = flow {
        when (val result = game.playRound()) {
            is Round.Finished -> {
                inMemoryHistory.add(state.value.hand?.copy(winner = result.winner?.name))
                emit(
                    WarOfSuitsState(
                        players = state.value.players,
                        hand = state.value.hand?.copy(winner = result.winner?.name),
                        syncState = WarOfSuitsSyncState.Finish
                    )
                )
            }
            is Round.Played -> {
                inMemoryHistory.add(result.hand)
                emit(
                    WarOfSuitsState(
                        players = state.value.players,
                        hand = result.hand,
                        rounds = result.remainingRound,
                        syncState = WarOfSuitsSyncState.Round
                    )
                )
            }
        }
    }
}