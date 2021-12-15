package com.kypcop.budget.fragments

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.kypcop.budget.Event
import com.kypcop.budget.R
import com.kypcop.budget.Tools
import com.kypcop.budget.adapters.EventAdapter
import com.kypcop.budget.fragments.EventCreationDialogFragment.Companion.EDIT
import com.kypcop.budget.fragments.EventCreationDialogFragment.Companion.EVENT_KEY
import com.kypcop.budget.fragments.EventCreationDialogFragment.Companion.EVENT_TYPE_KEY
import com.kypcop.budget.fragments.EventCreationDialogFragment.Companion.REASON

class PlannedExpFragment : Fragment() {

    lateinit var expensesListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_planned_exp, LinearLayout(activity), false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expensesListView = view.findViewById(R.id.planned_exp_lv)
        expensesListView.adapter = object : EventAdapter(requireContext(), Tools.plannedExpenses) {
            override fun onEditClick(position: Int) {
                findNavController().navigate(R.id.eventCreationDialogFragment, Bundle().apply {
                    putSerializable(EVENT_TYPE_KEY, Event.Type.PLANNED)
                    putString(REASON, EDIT)
                    putInt(Event.INDEX, position)
                })
            }

            override fun onDeleteClick(position: Int) {
                Tools.deletePlannedExpense(position)
                updateDisplayingData()
            }
        }
        val planButton = view.findViewById<Button>(R.id.plan_exp_btn).apply {
            setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    R.id.eventCreationDialogFragment,
                    Bundle().apply {
                        putSerializable(EVENT_TYPE_KEY, Event.Type.PLANNED)
                    })
            )
        }
    }

    fun updateDisplayingData() {
        (expensesListView.adapter as EventAdapter).notifyDataSetChanged()
    }



    companion object {
        const val TAG = "PlannedExpFragment"
    }
}