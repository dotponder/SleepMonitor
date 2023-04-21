package com.arnold.sleepmonitor.ui.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arnold.sleepmonitor.R
import com.arnold.sleepmonitor.databinding.FragmentRecordsBinding

class RecordsFragment : Fragment() {

    private var _binding: FragmentRecordsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dayButton = binding.recordsMenuDay
        val weekButton = binding.recordsMenuWeek
        val monthButton = binding.recordsMenuMonth
        val yearButton = binding.recordsMenuYear

        val dayFragment = DayFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.records_fragment_container, dayFragment)
        transaction.commit()

        dayButton.setOnClickListener() {
            val dayFragment = DayFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.records_fragment_container, dayFragment)
            transaction.commit()
        }

        weekButton.setOnClickListener() {
            val weekFragment = WeekFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.records_fragment_container, weekFragment)
            transaction.commit()
        }

        monthButton.setOnClickListener() {
            val monthFragment = MonthFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.records_fragment_container, monthFragment)
            transaction.commit()
        }

        yearButton.setOnClickListener() {
            val yearFragment = YearFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.records_fragment_container, yearFragment)
            transaction.commit()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}