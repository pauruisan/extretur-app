package com.uax.extretur.ui

import android.R.attr.data
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.uax.extretur.R
import com.uax.extretur.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import com.uax.extretur.databinding.EventPopupBinding
import com.uax.extretur.model.DayViewContainer
import com.uax.extretur.model.Evento
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var currentMonth: YearMonth = YearMonth.now()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        //aqui los savedInstanceState
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        setUpCalendar()

        binding.navMain.navLayout.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    binding.navMain.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        true
                    }

                    binding.navMain.navLayout.menu.findItem(R.id.foro).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ForumActivity::class.java)
                            startActivity(intent)
                        }
                        true
                    }

                    binding.navMain.navLayout.menu.findItem(R.id.perfil).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ProfileActivity::class.java)
                            startActivity(intent)
                        }
                        true
                    }
                    else -> false
                }
            }
        })

        acciones()

    }


    private fun acciones() {
        binding.btnMonumento.setOnClickListener(this)
        binding.btnGastro.setOnClickListener(this)
        binding.btnArbol.setOnClickListener(this)
    }

    private fun setUpCalendar() {
        val calendarView = binding.calendarioEventos
        val txtMesActual = binding.txtCalendario
        val btnMesPrevio = binding.btnMesPrevio
        val btnMesSiguiente = binding.btnMesSiguiente

        val today = LocalDate.now()
        val startMonth = YearMonth.from(today.minusMonths(12))
        val endMonth = YearMonth.from(today.plusMonths(12))

        binding.calendarioEventos.setup(startMonth, endMonth, DayOfWeek.MONDAY)
        binding.calendarioEventos.scrollToMonth(YearMonth.from(today))

        val eventos = listOf(
            Evento(LocalDate.of(2025, 5, 15), "San Isidro", "Fuente de Cantos"),
            Evento(LocalDate.of(2025, 4, 26), "Chanfaina", "Fuente de Cantos"),
            Evento(LocalDate.of(2025, 5, 22), "Emérita Lvdica", "Mérida"),
            Evento(LocalDate.of(2025, 4, 26), "Chanfaina", "Fuente de Cantos")
        )

        fun updateMonthHeader(month: YearMonth) {
            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("es", "ES"))
            txtMesActual.text = month.format(formatter).replaceFirstChar { it.uppercaseChar() }
        }

        calendarView.monthScrollListener = {
            currentMonth = it.yearMonth
            updateMonthHeader(currentMonth)
        }

        btnMesPrevio.setOnClickListener {
            val previousMonth = currentMonth.minusMonths(1)
            calendarView.smoothScrollToMonth(previousMonth)
        }

        btnMesSiguiente.setOnClickListener {
            val nextMonth = currentMonth.plusMonths(1)
            calendarView.smoothScrollToMonth(nextMonth)
        }

        updateMonthHeader(currentMonth)



        binding.calendarioEventos.dayViewResource = R.layout.calendar_day_layout
        binding.calendarioEventos.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.calendarDayText
                textView.text = day.date.dayOfMonth.toString()
                textView.setOnClickListener(null)

                if (day.position == DayPosition.MonthDate) {
                    val evento = eventos.find { it.fecha == day.date }

                    when (day.date) {
                        today -> {
                            textView.setBackgroundResource(R.drawable.today_circle_background)
                            textView.setTextColor(Color.WHITE)
                        }

                        evento?.fecha -> {
                            textView.setBackgroundResource(R.drawable.event_circle_background)
                            textView.setOnClickListener {
                                showEventoPopup(textView, evento)
                            }
                        }

                        else -> textView.background = null
                    }
                }
            }
        }
    }

    private fun showEventoPopup(anchor: View, evento: Evento?) {
        val inflater = LayoutInflater.from(anchor.context)
        val popupView = EventPopupBinding.inflate(inflater)

        popupView.txtTituloEvento.text = evento?.titulo
        popupView.txtUbicacionEvento.text = evento?.localizacion

        val popupWindow = PopupWindow(popupView.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow.elevation = 10f
        popupWindow.showAsDropDown(anchor)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnMonumento.id -> {
                val intent = Intent(applicationContext, MonumentsActivity::class.java)
                startActivity(intent)
            }

            binding.btnGastro.id -> {
                val intent = Intent(applicationContext, GastroActivity::class.java)
                startActivity(intent)
            }

            binding.btnArbol.id -> {
                val intent = Intent(applicationContext, NaturActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

