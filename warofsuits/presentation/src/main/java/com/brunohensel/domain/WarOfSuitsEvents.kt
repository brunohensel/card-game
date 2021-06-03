package com.brunohensel.domain

import com.brunohensel.core.cardtypes.warofsuits.Suits
import com.brunohensel.model.Hand

sealed class WarOfSuitsEvents {
    object PlayRound : WarOfSuitsEvents()
    object Start : WarOfSuitsEvents()
    object Restart : WarOfSuitsEvents()
    object History : WarOfSuitsEvents()
    object Rules : WarOfSuitsEvents()
}

sealed class WarOfSuitsSingleEvents {
    data class History(val history: List<Hand> = emptyList()) : WarOfSuitsSingleEvents()
    data class Rules(val suits: List<Suits> = emptyList()) : WarOfSuitsSingleEvents()
}
