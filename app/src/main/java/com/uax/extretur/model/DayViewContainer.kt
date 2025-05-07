package com.uax.extretur.model

import android.view.View
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.view.ViewContainer
import com.uax.extretur.databinding.CalendarDayLayoutBinding

class DayViewContainer (view: View) : ViewContainer(view) {
    val binding = CalendarDayLayoutBinding.bind(view)
    lateinit var day: CalendarDay
}