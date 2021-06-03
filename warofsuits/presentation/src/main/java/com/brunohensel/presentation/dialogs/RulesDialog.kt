package com.brunohensel.presentation.dialogs

import android.os.Bundle
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
            txtSuitsPriority.text = strBuilder.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}