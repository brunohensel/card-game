package com.brunohensel.presentation.dialogs

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.brunohensel.core.cardtypes.warofsuits.Suits
import com.brunohensel.presentation.R
import com.brunohensel.presentation.databinding.DialogRulesBinding


class RulesDialog(private val suits: List<Suits>) : DialogFragment() {

    private var binding: DialogRulesBinding? = null
    private val strBuilder by lazy { StringBuilder() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_CardGame)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogRulesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        strBuilder.apply {
            for (suit in suits) {
                append(suit)
                append(", ")
            }
        }

        binding?.run {
            txtDialogRulesTitle.setOnClickListener { dismiss() }
            txtRules.movementMethod = ScrollingMovementMethod()
            txtSuitsPriority.text = strBuilder.toString()
        }
    }

    //Prevent the rotation when the dialog fragment is shown
    override fun onResume() {
        super.onResume()
        //lock screen to portrait
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()
        //set rotation to sensor dependent
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}