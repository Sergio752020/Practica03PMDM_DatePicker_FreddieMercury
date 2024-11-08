package com.elmajuelo.datepickertareapmdm24_25

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elmajuelo.datepickertareapmdm24_25.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Establezco las variables para declarar fecha de la muerte y la que selecciona el usuario
    private val fechaMuerte = "24/11/1991"

    private var diaSeleccionado: Int? = null
    private var mesSeleccionado: Int? = null
    private var annioSeleccionado: Int? = null
    private var fechaSeleccionada = "$diaSeleccionado/$mesSeleccionado/$annioSeleccionado"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvfecha = binding.tvFecha
        val tvmensaje = binding.tvMensaje
        val btncalendario = binding.btnCalendario

        //Declaro e inicializo variables para que el calendario se abra en una fecha concreta
        val annioInicial = 1991
        val mesInicial = 0
        val diaInicial = 1

        //Creo el listener  para manejar la fecha seleccionada por usuario
        val dateListener = DatePickerDialog.OnDateSetListener { calendario, annio, mes, dia ->
            diaSeleccionado = dia
            mesSeleccionado = mes
            annioSeleccionado = annio
            fechaSeleccionada = "$dia/${mes.plus(1)}/$annio"
            tvfecha.text = fechaSeleccionada

            //Mediante when filtro las posibles opciones según los parámetros que se pasan al DatePickerDialog
            //Además voy dando pistas para averiguar rápidamente la fecha
            var comentario: String = when {
                fechaSeleccionada == fechaMuerte -> "¡CORRECTO!" + "\n" + "Freddie murió el 24/11/1991 a los 45 años"
                annio != 1991 -> "¡No cambies de año! Freddie murió en 1991"
                mes + 1 != 11 -> "No. Freddie murió en noviembre" + "\n" + "¿pero qué día?"
                dia in listOf(3, 10, 17) -> "Era un domingo pero no este"
                else -> "Fue en noviembre, pero no ese día." + "\n" + "Era un domingo"
            }
            tvmensaje.text = comentario

        }
        //Creo el DatePickerDialog con la fecha inicial y evito que se cierre si se pulsa fuera de él.
        val datePickerDialog =
            DatePickerDialog(this, dateListener, annioInicial, mesInicial, diaInicial)
        datePickerDialog.setCancelable(false)

        //Muestro el DatePickerDialog al pulsar el botón
        btncalendario.setOnClickListener {
            datePickerDialog.show()
        }

    }
}

