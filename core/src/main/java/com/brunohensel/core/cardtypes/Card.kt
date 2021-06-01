package com.brunohensel.core.cardtypes

import androidx.annotation.DrawableRes
import com.brunohensel.core.cardtypes.warofsuits.BaseRank
import com.brunohensel.core.cardtypes.warofsuits.BaseSuits

data class Card(val rank: BaseRank, val suit: BaseSuits, @DrawableRes val front: Int = 0)


