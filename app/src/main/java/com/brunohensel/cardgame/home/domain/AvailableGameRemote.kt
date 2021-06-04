package com.brunohensel.cardgame.home.domain

import com.brunohensel.core.Either
import com.brunohensel.cardgame.home.domain.model.AvailableGame

interface AvailableGameRemote {
    suspend fun fetchAvailableGames(): Either<Throwable, List<AvailableGame>>
}