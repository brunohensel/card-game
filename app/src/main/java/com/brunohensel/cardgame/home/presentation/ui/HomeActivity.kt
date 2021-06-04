package com.brunohensel.cardgame.home.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.brunohensel.cardgame.R
import com.brunohensel.cardgame.application.component
import com.brunohensel.cardgame.databinding.ActivityHomeBinding
import com.brunohensel.cardgame.home.domain.module.GameType
import com.brunohensel.cardgame.home.domain.state.HomeState
import com.brunohensel.cardgame.home.domain.state.HomeSyncState
import com.brunohensel.cardgame.home.presentation.HomeViewModel
import com.brunohensel.core.utils.collectIn
import com.brunohensel.presentation.WarOfSuitsActivity
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityHomeBinding
    private val homeAdapter = HomeAdapter(::onClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        component.activityComponent().create().inject(this)

        viewModel
            .state
            .onEach { state -> renderState(state) }
            .collectIn(this)

        with(binding) {
            rvAvailableGames.apply {
                adapter = homeAdapter
            }
        }
    }

    private fun renderState(state: HomeState) {
        when (state.syncState) {
            HomeSyncState.Content -> homeAdapter.submitList(state.availableGames)
            HomeSyncState.Idle -> { /* no action is needed */ }
            HomeSyncState.Message -> { /* handle error state */ }
        }
    }

    private fun onClick(gameType: GameType) {
        when (gameType) {
            GameType.WAR_OF_SUITS -> startActivity(Intent(this, WarOfSuitsActivity::class.java))
            GameType.POKER -> Toast.makeText(this, getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
            GameType.BLACKJACK -> Toast.makeText(this, getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
        }
    }
}