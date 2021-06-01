package com.brunohensel.cardgame.home.domain.state
/**
 * This class represents the sync state of the UI, the UI will be rendered for each sync state.
 * I.e the [Content] indicates a Success condition, where is safe to display some content, in the
 * other hand, the [Message] indicates that some Error has occurred, and a message should be
 * displayed to let the user know what just happened.
 */
sealed class HomeSyncState{
    object Idle : HomeSyncState()
    object Message : HomeSyncState()
    object Content : HomeSyncState()
}