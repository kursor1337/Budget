package com.kypcop.budget.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.kypcop.budget.R
import com.kypcop.budget.Tools

class GraphFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_graph, LinearLayout(activity), false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lineChart = view.findViewById<LineChart>(R.id.line_chart)
        setDataToChart(lineChart)
    }


    private fun setDataToChart(lineChart: LineChart) {
        val entries = ArrayList<Entry>()
        for (i in 0 until Tools.monthList.size) {
            val month = Tools.monthList[i]
            entries.add(Entry(i.toFloat(), month.balance().toFloat()))
        }
        val dataSet = LineDataSet(entries, "Balance")
        dataSet.color = R.color.transparent_blue
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return if (value % 1 == 0f) {
                    val month = Tools.monthList[value.toInt()]
                    return "${month.monthName.getLocalizedName(requireContext())} ${month.year}"
                } else {
                    ""
                }
            }
        }
    }
}