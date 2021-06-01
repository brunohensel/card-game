package com.brunohensel.cardgame.home.presentation

import com.brunohensel.cardgame.fakes.FakeRemoteDataSourceFailed
import com.brunohensel.cardgame.fakes.FakeRemoteDataSourceSuccess
import com.brunohensel.cardgame.home.datasource.remote.AvailableGamesList
import com.brunohensel.cardgame.home.domain.AvailableGameRemote
import com.brunohensel.cardgame.home.domain.state.HomeState
import com.brunohensel.cardgame.home.domain.state.HomeSyncState
import com.brunohensel.test.BaseUnitTest
import com.brunohensel.test.coroutines.test
import com.brunohensel.test.coroutines.testCoroutine
import org.junit.Test
import java.io.IOException

class HomeViewModelTest: BaseUnitTest<HomeViewModel>(){
    private val fakeRemoteSource: AvailableGameRemote = FakeRemoteDataSourceSuccess()
    private val exception = IOException()
    private val fakeRemoteFailed: AvailableGameRemote = FakeRemoteDataSourceFailed(exception)
    override fun sut(): HomeViewModel = HomeViewModel(fakeRemoteSource)

    @Test
    fun `emit HomeState when remote Fetch is successful`() = testCoroutine {
        val result = tested.state.test(this)
        val expected = HomeState(AvailableGamesList(), syncState = HomeSyncState.Content)
        result.assertFirst(expected).finish()
    }

    @Test
    fun `emit HomeState when remote Fetch is failed`() = testCoroutine {
        val itSelf = HomeViewModel(fakeRemoteFailed)
        val result = itSelf.state.test(this)
        val expected = HomeState(emptyList(),exception, syncState = HomeSyncState.Message)
        result.assertFirst(expected).finish()
    }
}