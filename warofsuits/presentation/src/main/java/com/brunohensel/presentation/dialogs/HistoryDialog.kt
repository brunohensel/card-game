package com.brunohensel.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.brunohensel.model.Hand
import com.brunohensel.presentation.HistoryAdapter
import com.brunohensel.presentation.R
import com.brunohensel.presentation.databinding.DialogHistoryBinding


class HistoryDialog(private val list: List<Hand>) : DialogFragment() {

    private val historyAdapter = HistoryAdapter()
    private var binding: DialogHistoryBinding? = null

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
        binding = DialogHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            rvDialogHistory.adapter = historyAdapter
            txtDialogTitle.setOnClickListener { dismiss() }
            txtNoHistory.isVisible = list.isEmpty()
            imgEmptyState.isVisible = list.isEmpty()
        }
        historyAdapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}