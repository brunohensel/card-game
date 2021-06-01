package com.brunohensel.presentation

import com.brunohensel.WarOfSuits
import com.brunohensel.core.base.viewmodel.BaseStateViewModel
import com.brunohensel.domain.WarOfSuitsEvents
import com.brunohensel.domain.WarOfSuitsEvents.PlayRound
import com.brunohensel.domain.WarOfSuitsEvents.Start
import com.brunohensel.domain.state.WarOfSuitsState
import com.brunohensel.domain.state.WarOfSuitsSyncState
import com.brunohensel.model.Round
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WarOfSuitsViewModel @Inject constructor(
    private val game: WarOfSuits
) : BaseStateViewModel<WarOfSuitsState, WarOfSuitsEvents>(initialState = WarOfSuitsState(), initialEvent = Start) {

    override fun process(event: WarOfSuitsEvents): Flow<WarOfSuitsState> {
        return when (event) {
            PlayRound -> playRound()
            Start -> startGame()
        }
    }

    private fun startGame(): Flow<WarOfSuitsState> = flow {
        game.start()
        emit(WarOfSuitsState(game.getPlayers(), syncState = WarOfSuitsSyncState.Started))
    }

    private fun playRound(): Flow<WarOfSuitsState> = flow {
        when (val result = game.playRound()) {
            is Round.Finished -> {
                emit(WarOfSuitsState(winner = result.winner, syncState = WarOfSuitsSyncState.Finish))
            }
            is Round.RoundWinner -> emit(
                WarOfSuitsState(
                    players = result.players,
                    winner = result.winner,
                    hand = result.hand,
                    syncState = WarOfSuitsSyncState.Round
                )
            )
        }
    }
}