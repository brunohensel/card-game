package com.brunohensel.cardgame.home.presentation.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.brunohensel.cardgame.application.component
import com.brunohensel.cardgame.databinding.ActivityHomeBinding
import com.brunohensel.cardgame.home.domain.module.GameType
import com.brunohensel.cardgame.home.domain.state.HomeState
import com.brunohensel.cardgame.home.domain.state.HomeSyncState
import com.brunohensel.cardgame.home.presentation.HomeViewModel
import com.brunohensel.core.utils.collectIn
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
            .collectIn(this@HomeActivity)

        with(binding) {
            rvAvailableGames.apply {
                adapter = homeAdapter
                layoutManager = GridLayoutManager(this@HomeActivity, 2)
            }
        }
    }

    private fun renderState(state: HomeState) {
        handleLoadingAnimation(state.isLoading)
        when (state.syncState) {
            HomeSyncState.Content -> homeAdapter.submitList(state.availableGames)
            HomeSyncState.Idle -> { }
            HomeSyncState.Message -> { }
        }
    }

    private fun handleLoadingAnimation(shouldShow: Boolean) {
        with(binding.lottieLoading) {
            isVisible = shouldShow
            if (shouldShow) playAnimation() else cancelAnimation()
        }
    }

    private fun onClick(gameType: GameType) {
        when (gameType) {
            //GameType.WAR_OF_SUITS -> startActivity(Intent(this, WarOfSuitsActivity::class.java))
            GameType.POKER -> Toast.makeText(this, "Open Poker", Toast.LENGTH_SHORT).show()
            GameType.BLACKJACK -> Toast.makeText(this, "Open Blackjack", Toast.LENGTH_SHORT).show()
        }
    }
}