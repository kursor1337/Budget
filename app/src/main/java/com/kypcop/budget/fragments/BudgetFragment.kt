package com.kypcop.budget.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kypcop.budget.Event
import com.kypcop.budget.Month
import com.kypcop.budget.R
import com.kypcop.budget.Tools
import com.kypcop.budget.adapters.EventAdapter
import com.kypcop.budget.fragments.EventCreationDialogFragment.Companion.CREATE
import com.kypcop.budget.fragments.EventCreationDialogFragment.Companion.EDIT
import com.kypcop.budget.fragments.EventCreationDialogFragment.Companion.EVENT_TYPE_KEY
import com.kypcop.budget.fragments.EventCreationDialogFragment.Companion.REASON

/**
 * A simple [Fragment] subclass.
 * Use the [BudgetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BudgetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var monthPos = -1
    private lateinit var month: Month

    // views
    lateinit var incomeListView: ListView
    lateinit var outcomeListView: ListView
    lateinit var totalIncomeTextView: TextView
    lateinit var totalOutcomeTextView: TextView
    lateinit var balanceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            monthPos = it.getInt(MONTH_POSITION)
            month = Tools.monthList[monthPos]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incomeListView = view.findViewById(R.id.income_lv)
        outcomeListView = view.findViewById(R.id.outcome_lv)
        totalIncomeTextView = view.findViewById(R.id.total_income_tv)
        totalOutcomeTextView = view.findViewById(R.id.total_outcome_tv)
        balanceTextView = view.findViewById(R.id.balance)

        val addIncomeButton = view.findViewById<Button>(R.id.addIncome_btn).apply {
            setOnClickListener(navigateOnClickListener(Event.Type.INCOME))
        }
        val addOutcomeButton = view.findViewById<Button>(R.id.addOutcome_btn).apply {
            setOnClickListener(navigateOnClickListener(Event.Type.OUTCOME))
        }

        incomeListView.adapter = object : EventAdapter(requireContext(), month.incomeList) {
            override fun onEditClick(position: Int) {
                val event = month.incomeList[position]
                findNavController().navigate(R.id.eventCreationDialogFragment, Bundle().apply {
                    putSerializable(EVENT_TYPE_KEY, Event.Type.INCOME)
                    putString(REASON, EDIT)
                    putInt(Event.INDEX, position)
                    putInt(Month.INDEX, monthPos)
                })
                updateDisplayingData()
            }

            override fun onDeleteClick(position: Int) {
                val event = month.incomeList[position]
                month.deleteEvent(event)
                updateDisplayingData()
            }
        }

        outcomeListView.adapter = object : EventAdapter(requireContext(), month.outcomeList) {
            override fun onEditClick(position: Int) {
                val event = month.outcomeList[position]
                findNavController().navigate(R.id.eventCreationDialogFragment, Bundle().apply {
                    putSerializable(EVENT_TYPE_KEY, Event.Type.OUTCOME)
                    putString(REASON, EDIT)
                    putInt(Event.INDEX, position)
                    putInt(Month.INDEX, monthPos)
                })
                updateDisplayingData()
            }

            override fun onDeleteClick(position: Int) {
                val event = month.outcomeList[position]
                month.deleteEvent(event)
                updateDisplayingData()
            }
        }
        updateDisplayingData()
    }

    private fun navigateOnClickListener(eventType: Event.Type): View.OnClickListener {
        return View.OnClickListener {
            findNavController().navigate(R.id.eventCreationDialogFragment, Bundle().apply {
                putSerializable(EVENT_TYPE_KEY, eventType)
                putString(REASON, CREATE)
                putInt(Month.INDEX, monthPos)
            })
            updateDisplayingData()
        }
    }

    fun updateDisplayingData() {
        totalIncomeTextView.text = "+${month.totalIncome()}"
        totalOutcomeTextView.text = "-${month.totalOutcome()}"
        balanceTextView.text = getString(R.string.balance, month.balance())
        balanceTextView.setBackgroundResource(
            when {
                month.balance() < 0 -> R.color.transparent_red
                month.balance() > 0 -> R.color.transparent_blue
                else -> R.color.white
            }
        )
        (incomeListView.adapter as EventAdapter).notifyDataSetChanged()
        (outcomeListView.adapter as EventAdapter).notifyDataSetChanged()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

        const val TAG = "BudgetFragment"

        const val MONTH_POSITION = "month"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param month month which's budget u wanna see.
         * @return A new instance of fragment BudgetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(month: Month) =
            BudgetFragment().apply {
                arguments = Bundle().apply {
                    putString(MONTH_POSITION, Tools.gson.toJson(month))
                }
            }
    }
}