package com.brunohensel.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.brunohensel.core.utils.collectIn
import com.brunohensel.di.WarOfSuitsComponentProvider
import com.brunohensel.domain.state.WarOfSuitsState
import com.brunohensel.domain.state.WarOfSuitsSyncState
import com.brunohensel.model.Hand
import com.brunohensel.model.Player
import com.brunohensel.presentation.databinding.ActivityWarOfSuitsBinding
import com.brunohensel.domain.WarOfSuitsEvents
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
    }

    private fun renderState(state: WarOfSuitsState) {
        when (state.syncState) {
            WarOfSuitsSyncState.Finish -> Toast.makeText(
                this,
                "Finished, player:${state.winner} won",
                Toast.LENGTH_SHORT
            ).show()
            WarOfSuitsSyncState.Idle -> Toast.makeText(this, "Idle", Toast.LENGTH_SHORT).show()
            WarOfSuitsSyncState.Round -> showRound(state.winner, state.players, state.hand)
            WarOfSuitsSyncState.Started -> setPlayersConfig(state.players)
        }
    }

    private fun setPlayersConfig(players: Pair<Player, Player>?) {
        with(binding) {
            txtPlayerOneName.text = players?.run { first.name }
            txtPlayerTwoName.text = players?.run { second.name }
            txtPlayerOneRegularDeque.text = players?.run { first.regularPile.cards.size.toString() }
            txtPlayerTwoRegularDeque.text =
                players?.run { second.regularPile.cards.size.toString() }
        }
    }

    private fun showRound(
        winner: Player?,
        players: Pair<Player, Player>?,
        hand: Hand?,
    ) {
        with(binding) {
            players?.run {
                txtPlayerOneRegularDeque.text = first.regularPile.cards.size.toString()
                txtPlayerTwoRegularDeque.text = second.regularPile.cards.size.toString()
                txtPlayerOneDiscardDeque.text = first.discardPile.cards.size.toString()
                txtPlayerTwoDiscardDeque.text = second.discardPile.cards.size.toString()

                first.layDownCard?.front?.let {
                    imgCardPlayerOne.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@WarOfSuitsActivity,
                            it
                        )
                    )
                }
                second.layDownCard?.front?.let {
                    imgCardPlayerTwo.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@WarOfSuitsActivity,
                            it
                        )
                    )
                }
            }
        }
        Toast.makeText(this, "Winner: ${winner?.name}", Toast.LENGTH_SHORT).show()
    }
}