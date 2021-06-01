package com.brunohensel.cardgame.home.presentation

import android.util.Log
import com.brunohensel.cardgame.home.domain.AvailableGameRemote
import com.brunohensel.cardgame.home.domain.HomeEvents
import com.brunohensel.cardgame.home.domain.HomeEvents.Fetch
import com.brunohensel.cardgame.home.domain.state.HomeState
import com.brunohensel.cardgame.home.domain.state.HomeSyncState
import com.brunohensel.cardgame.home.domain.state.HomeSyncState.Content
import com.brunohensel.cardgame.home.domain.state.HomeSyncState.Message
import com.brunohensel.core.Either
import com.brunohensel.core.annotations.ActivityScope
import com.brunohensel.core.base.viewmodel.BaseStateViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Class that extends the [BaseStateViewModel] and is responsible to process the [HomeEvents], map
 * them into [HomeState].
 *
 * @param [remote] is the contract with the remote data source, which will fetch us some data.
 */
class HomeViewModel @Inject constructor(
    private val remote: AvailableGameRemote
) : BaseStateViewModel<HomeState, HomeEvents>(initialState = HomeState(), initialEvent = Fetch) {

    override fun process(event: HomeEvents): Flow<HomeState> {
        return when (event) {
            Fetch -> fetchRemoteAvailableGame()
        }
    }

    private fun fetchRemoteAvailableGame(): Flow<HomeState> = flow {
        when (val result = remote.fetchAvailableGames()) {
            is Either.Left  -> emit(HomeState(failure = result.value, syncState = Message))
            is Either.Right -> emit(HomeState(availableGames = result.value, syncState = Content))
        }
    }.onStart { emit(HomeState(isLoading = true)) }
}