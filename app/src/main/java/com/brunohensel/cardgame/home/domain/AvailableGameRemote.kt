package com.brunohensel.cardgame.home.domain

import com.brunohensel.core.Either
import com.brunohensel.cardgame.home.domain.module.AvailableGame

interface AvailableGameRemote {
    suspend fun fetchAvailableGames(): com.brunohensel.core.Either<Throwable, List<AvailableGame>>
}