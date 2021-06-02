package com.brunohensel.domain

sealed class WarOfSuitsEvents{
    object PlayRound: WarOfSuitsEvents()
    object Start: WarOfSuitsEvents()
    object Restart: WarOfSuitsEvents()
}
