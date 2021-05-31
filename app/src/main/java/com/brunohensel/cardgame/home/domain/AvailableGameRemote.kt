package com.brunohensel.cardgame.home.domain

import com.brunohensel.cardgame.Either
import com.brunohensel.cardgame.home.domain.module.AvailableGame

interface AvailableGameRemote {
    suspend fun fetchAvailableGames(): Either<Throwable, List<AvailableGame>>
}