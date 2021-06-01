package com.brunohensel.cardgame.fakes

import com.brunohensel.cardgame.home.datasource.remote.AvailableGamesList
import com.brunohensel.cardgame.home.domain.AvailableGameRemote
import com.brunohensel.cardgame.home.domain.module.AvailableGame
import com.brunohensel.core.Either

class FakeRemoteDataSourceSuccess : AvailableGameRemote {

    override suspend fun fetchAvailableGames(): Either<Throwable, List<AvailableGame>> {
        return Either.Right(AvailableGamesList())
    }
}