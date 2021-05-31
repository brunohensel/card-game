package com.brunohensel.cardgame.home.datasource.remote

import com.brunohensel.cardgame.Either
import com.brunohensel.cardgame.home.domain.AvailableGameRemote
import com.brunohensel.cardgame.home.domain.module.AvailableGame
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class responsible to "fetch" or simulating a fetch request to an remote api that returns as a
 * response the AvailableGame object or an exception.
 */
@Singleton
class AvailableGameRemoteImpl @Inject constructor(): AvailableGameRemote {

    override suspend fun fetchAvailableGames(): Either<Throwable, List<AvailableGame>> {
        delay(1000L) //Simulating a success network call with a delay
        return Either.Right(AvailableGamesList())
    }
}