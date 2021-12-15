package com.kypcop.budget.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kypcop.budget.MainActivity
import com.kypcop.budget.Tools
import com.kypcop.budget.adapters.MonthsAdapter
import com.kypcop.budget.R

class MonthsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_months, LinearLayout(activity), false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addMonthButton = view.findViewById<Button>(R.id.add_month_btn).apply {
            setOnClickListener {
                //TODO
            }
        }

        val monthsListView = view.findViewById<ListView>(R.id.months_lv)
        monthsListView.adapter = MonthsAdapter(requireContext(), Tools.monthList)
        monthsListView.setOnItemClickListener { parent, view, position, id ->
            Navigation.findNavController(view).navigate(R.id.budgetFragment, Bundle().apply {
                putInt(BudgetFragment.MONTH_POSITION, position)
            })
        }
    }
}