package uz.gita.waterappgoogleplaygb.presentetion.screens.pages

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.waterappgoogleplaygb.R
import uz.gita.waterappgoogleplaygb.databinding.ScreenStatisticBinding
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.StatisticsViewModel
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.impl.StatisticsViewModelImpl
import uz.gita.waterappgoogleplaygb.utils.extensions.getDateFormat
import uz.gita.waterappgoogleplaygb.utils.extensions.returnDateAndThen
import uz.gita.waterappgoogleplaygb.utils.extensions.toDate
import java.util.*

@AndroidEntryPoint
class StatisticScreen:Fragment(R.layout.screen_statistic) {
    private val viewBinding: ScreenStatisticBinding by viewBinding(ScreenStatisticBinding::bind)

    private val viewModel: StatisticsViewModel by viewModels<StatisticsViewModelImpl>()

    private var startDate = 0L
    private var endDate = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pair = returnDateAndThen()
        val st = pair?.first.toString()
        val ed = pair?.second.toString()
        val start = st.toDate()
        val end = ed.toDate()
        startDate = start.time
        endDate = end.time
        viewBinding.tvStartDate.text = getDateFormat(start)
        viewBinding.tvEndDate.text = getDateFormat(end)

        viewBinding.fabChooseDate.setOnClickListener {
            openRangeCalendar()
        }

        viewModel.statisticsFlow.onEach {
            val barList = ArrayList<BarEntry>()
            for (i in it.indices) {
                barList.add(BarEntry(i.toFloat(), if (it[i] == null) 0f else it[i].toFloat()))
            }
            setBarChart(barList)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun openRangeCalendar() {
        val dialog =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Choose data range")
                .build()
        dialog.addOnPositiveButtonClickListener {

            viewModel.changeDataRange(it.first, it.second)

            viewBinding.tvStartDate.text = getDateFormat(Date(it.first))

            viewBinding.tvEndDate.text = getDateFormat(Date(it.second))

        }
        dialog.show(childFragmentManager, "dia")
    }

    private fun setBarChart(list: List<BarEntry>) {
        val barData = BarDataSet(list, "Statistics")
        barData.color = Color.parseColor("#42C5FD")
        barData.valueTextColor = Color.parseColor("#42C5FD")
        barData.barBorderColor = Color.parseColor("#ffffff")
        barData.valueTextSize = 13f
        val bar = BarData(barData)
        viewBinding.statisticsLineChart.apply {
            setFitBars(true)
            data = bar
            setNoDataTextColor(Color.parseColor("#42C5FD"))
            description.text = "Water"
            animateY(1000)
        }
    }

    override fun onResume() {
        super.onResume()
//        if (Utils.changeData) {
//            viewModel.changeDataRange(startDate, endDate)
//            Utils.changeData = false
//        }
    }
}