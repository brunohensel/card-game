package com.brunohensel.cardgame.fakes

import com.brunohensel.cardgame.home.domain.AvailableGameRemote
import com.brunohensel.cardgame.home.domain.module.AvailableGame
import com.brunohensel.core.Either

class FakeRemoteDataSourceFailed(private val exception: Throwable) : AvailableGameRemote {

    override suspend fun fetchAvailableGames(): Either<Throwable, List<AvailableGame>> {
        return Either.Left(exception)
    }
}