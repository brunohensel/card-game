package com.brunohensel.datasource.local

import com.brunohensel.core.cardtypes.Card

interface WarOfSuitLocalDataSource {
    fun fetchDeckOfCards(): List<Card>
}