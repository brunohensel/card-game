package com.brunohensel.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.brunohensel.core.utils.collectIn
import com.brunohensel.di.WarOfSuitsComponentProvider
import com.brunohensel.domain.WarOfSuitsEvents
import com.brunohensel.domain.state.WarOfSuitsState
import com.brunohensel.domain.state.WarOfSuitsSyncState.*
import com.brunohensel.model.Hand
import com.brunohensel.model.Player
import com.brunohensel.presentation.databinding.ActivityWarOfSuitsBinding
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class WarOfSuitsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: WarOfSuitsViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityWarOfSuitsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WarOfSuitsComponentProvider)
            .provideWarOfSuitsComponent()
            .inject(this@WarOfSuitsActivity)
        super.onCreate(savedInstanceState)
        binding = ActivityWarOfSuitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel
            .state
            .onEach { state -> renderState(state) }
            .collectIn(this)

        binding.btnPlayRound.setOnClickListener { viewModel.dispatch(WarOfSuitsEvents.PlayRound) }
        binding.imgCloseGame.setOnClickListener { finish() }
        binding.imgResetGame.setOnClickListener { viewModel.dispatch(WarOfSuitsEvents.Restart) }
    }

    private fun renderState(state: WarOfSuitsState) {
        when (state.syncState) {
            Finish  -> showFinishedRound(state.hand)
            Idle    -> {}
            Round   -> showRoundInfo(state.hand)
            Started -> setPlayersConfig(state.players)
            Restarted -> handleRestartState()
        }
    }

    private fun handleRestartState() {
        with(binding){
            txtPlayerOneScore.text = "0"
            txtPlayerTwoScore.text = "0"
            imgCardPlayerOne.setImageDrawable(null)
            imgCardPlayerTwo.setImageDrawable(null)
            txtRoundWinner.text = ""
        }
    }

    private fun showFinishedRound(hand: Hand?) {
        hand?.run {
            binding.txtRoundWinner.text = winner?.name ?: "The game has tied"
        }
    }

    private fun setPlayersConfig(players: Pair<Player, Player>?) {
        with(binding) {
            players?.run {
                txtPlayerOneName.text =  first.name
                txtPlayerTwoName.text =  second.name
            }
        }
    }

    private fun showRoundInfo(hand: Hand?) {
        with(binding) {
            hand?.run {
                txtPlayerOneScore.text = playerOneScore.toString()
                txtPlayerTwoScore.text = playerTwoScore.toString()
                imgCardPlayerOne.setImageDrawable(getDrawableFromRes(playedHands.first.front))
                imgCardPlayerTwo.setImageDrawable(getDrawableFromRes(playedHands.second.front))
                txtRoundWinner.text = winner?.name
            }
        }
    }

    private fun getDrawableFromRes(it: Int) = ContextCompat.getDrawable(this, it)
}