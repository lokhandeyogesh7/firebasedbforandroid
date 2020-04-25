package com.wns.covidupdateapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_calendar.*


class CalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            println("selected date $day/${month+1}/$year")
            var dateStr = "${month+1}/$day/$year".trim()
            startActivity(
                Intent(activity, DayWiseListActivity::class.java).putExtra(
                    "date",
                    dateStr
                )
            )
        }

    }

}
