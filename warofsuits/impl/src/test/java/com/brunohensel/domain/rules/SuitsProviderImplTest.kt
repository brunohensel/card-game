package com.brunohensel.domain.rules

import org.junit.jupiter.api.Test

internal class SuitsProviderImplTest {

    private val sut = SuitsProviderImpl()
    @Test
    fun getShuffledSuits() {
        //Given
        val init = sut.shuffledSuits
        //When
        sut.shuffleSuits()
        val final = sut.shuffledSuits
            //Then
        assert(init != final)
    }
}