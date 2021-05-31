package com.brunohensel.cardgame.home.domain
/**
 * Represents all the events that can be triggered by the view.
 * Every event will trigger some action that will be translated later on in the Ui state.
 *
 * [Fetch] will trigger a call to the [AvailableGameRemote] in order to fetch some data from the
 * api.
 */
sealed class HomeEvents {
    object Fetch : HomeEvents()
}
