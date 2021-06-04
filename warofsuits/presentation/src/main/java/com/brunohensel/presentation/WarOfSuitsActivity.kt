package com.brunohensel.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.brunohensel.core.cardtypes.warofsuits.Suits
import com.brunohensel.core.utils.collectIn
import com.brunohensel.di.WarOfSuitsComponentProvider
import com.brunohensel.domain.WarOfSuitsEvents
import com.brunohensel.domain.WarOfSuitsSingleEvents
import com.brunohensel.domain.WarOfSuitsSingleEvents.History
import com.brunohensel.domain.WarOfSuitsSingleEvents.Rules
import com.brunohensel.domain.state.WarOfSuitsState
import com.brunohensel.domain.state.WarOfSuitsSyncState.*
import com.brunohensel.getDrawableFromRes
import com.brunohensel.model.Hand
import com.brunohensel.model.Player
import com.brunohensel.presentation.databinding.ActivityWarOfSuitsBinding
import com.brunohensel.presentation.dialogs.HistoryDialog
import com.brunohensel.presentation.dialogs.RulesDialog
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class WarOfSuitsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: WarOfSuitsViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityWarOfSuitsBinding
    private var isEndTheGame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WarOfSuitsComponentProvider)
            .provideWarOfSuitsComponent()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityWarOfSuitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel
            .state
            .onEach { state -> renderState(state) }
            .collectIn(this)

        viewModel
            .oneShotEvent
            .onEach { singleEvent -> processSingleEvent(singleEvent) }
            .collectIn(this)

        with(binding) {
            imgCloseGame.setOnClickListener { finish() }
            imgResetGame.setOnClickListener { viewModel.dispatch(WarOfSuitsEvents.Restart) }
            btnShowHistory.setOnClickListener { viewModel.dispatch(WarOfSuitsEvents.History) }
            btnShowRules.setOnClickListener { viewModel.dispatch(WarOfSuitsEvents.Rules) }
            btnPlayRound.setOnClickListener { handleNextOrLastRound() }
        }
    }

    private fun handleNextOrLastRound() {
        if (isEndTheGame) {
            viewModel.dispatch(WarOfSuitsEvents.Restart)
            setDefaultValuesForNewGame()
        } else {
            viewModel.dispatch(WarOfSuitsEvents.PlayRound)
        }
    }

    private fun setDefaultValuesForNewGame() {
        isEndTheGame = false
        binding.btnPlayRound.text = getString(R.string.play_round)
    }

    private fun processSingleEvent(singleEvent: WarOfSuitsSingleEvents) {
        when (singleEvent) {
            is History -> showHistoryDialog(singleEvent.history)
            is Rules -> showRulesDialog(singleEvent.suits)
        }
    }

    private fun showRulesDialog(suits: List<Suits>) {
        RulesDialog(suits).show(supportFragmentManager, "Rules")
    }

    private fun showHistoryDialog(history: List<Hand>) {
        HistoryDialog(history).show(supportFragmentManager, "Dialog")
    }

    private fun renderState(state: WarOfSuitsState) {
        when (state.syncState) {
            Finish -> showFinishedRound(state.hand)
            Idle -> { }
            Round -> showRoundInfo(state.rounds, state.hand, state.players)
            Started -> setPlayersConfig(state.players)
            Restarted -> handleRestartState()
        }
    }

    private fun handleRestartState() {
        with(binding) {
            txtPlayerOneScore.text = "0"
            txtPlayerTwoScore.text = "0"
            imgCardPlayerOne.setImageDrawable(null)
            imgCardPlayerTwo.setImageDrawable(null)
            txtRoundWinner.text = ""
            txtGameWinner.text = ""
            setDefaultValuesForNewGame()
        }
    }

    private fun showFinishedRound(hand: Hand?) {
        hand?.run {
            binding.btnPlayRound.text = getString(R.string.play_again)
            isEndTheGame = true
            binding.txtGameWinner.text = winner ?: getString(R.string.game_tied)
        }
    }

    private fun setPlayersConfig(players: Pair<Player, Player>?) {
        with(binding) {
            players?.run {
                txtPlayerOneName.text = first.name
                txtPlayerTwoName.text = second.name
            }
        }
    }

    private fun showRoundInfo(rounds: Int, hand: Hand?, players: Pair<Player, Player>?) {
        with(binding) {
            hand?.run {
                txtRemainingRounds.text = rounds.toString()
                txtPlayerOneScore.text = playerOneScore.toString()
                txtPlayerTwoScore.text = playerTwoScore.toString()
                imgCardPlayerOne.setImageDrawable(getDrawableFromRes(playedHands.first.front))
                imgCardPlayerTwo.setImageDrawable(getDrawableFromRes(playedHands.second.front))
                txtRoundWinner.text = winner
            }
            players?.run {
                txtPlayerOneName.text = first.name
                txtPlayerTwoName.text = second.name
            }
        }
    }
}