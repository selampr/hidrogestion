package tfg.selampr.hidrogestion.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tfg.selampr.hidrogestion.R
import tfg.selampr.hidrogestion.data.database.AppDatabase
import tfg.selampr.hidrogestion.data.model.NeighborEntity
import tfg.selampr.hidrogestion.data.model.WaterCutEntity
import tfg.selampr.hidrogestion.ui.activities.MainActivity
import tfg.selampr.hidrogestion.ui.dialogs.AlertDialogFragment
import tfg.selampr.hidrogestion.utils.MailSender
import java.text.SimpleDateFormat
import java.util.*

class CorteFragment : Fragment() {

    private var zonaSeleccionada: String? = null
    private var poligonoSeleccionado: Polygon? = null
    private val zonasPoligonos = mutableMapOf<String, Polygon>()
    private var zonaPendienteDeResaltar: String? = null

    private lateinit var etInicio: EditText
    private lateinit var etFin: EditText
    private lateinit var etInfo: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCerrar: ImageView
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_corte, container, false)

        val corteId = arguments?.getInt("corteId") ?: -1

        etInicio = view.findViewById(R.id.etInicio)
        etFin = view.findViewById(R.id.etFin)
        etInfo = view.findViewById(R.id.etInfo)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCerrar = view.findViewById(R.id.btnCerrar)

        val tvTitulo = view.findViewById<TextView>(R.id.tvTitulo)
        val tvSubtitulo = view.findViewById<TextView>(R.id.tvSubtitulo)

        if (corteId != -1) {
            tvSubtitulo.text = "Detalle del corte ID: $corteId"
            tvTitulo.text = "DETALLES"
        } else {
            tvSubtitulo.text = "Añadir nuevo"
            tvTitulo.text = "CORTE DE AGUA"
        }

        configurarFechaHoraPickers()

        // Carga del mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_container)
                as? SupportMapFragment ?: SupportMapFragment.newInstance()

        childFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()

        mapFragment.getMapAsync { map ->
            googleMap = map
            cargarZonasEnMapa()

            // Pinta la zona si viene de un corte ya guardado
            zonaPendienteDeResaltar?.let { zona ->
                resaltarZonaSeleccionada(zona)
            }

            val centro = LatLng(41.984900, -1.270900)
            googleMap.moveCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.Builder()
                        .target(centro)
                        .zoom(15.9f)
                        .build()
                )
            )
        }

        btnGuardar.setOnClickListener {
            guardarCorte()
        }

        btnCerrar.setOnClickListener {
            findNavController().navigateUp()
        }

        // Si es un corte existente, cargar datos y bloquear campos
        if (corteId != -1) {
            lifecycleScope.launch {
                val corte = AppDatabase.getInstance(requireContext()).waterCutDao().getWaterCutById(corteId)
                corte?.let {
                    zonaSeleccionada = it.zone
                    requireActivity().runOnUiThread {
                        resaltarZonaSeleccionada(zonaSeleccionada!!)
                    }


                    etInicio.setText(it.startTime)
                    etFin.setText(it.endTime)
                    etInfo.setText(it.reason)
                    etInicio.isEnabled = false
                    etFin.isEnabled = false
                    etInfo.isEnabled = false
                    btnGuardar.visibility = View.GONE
                }
            }
        }

        // Listeners de cambios en texto
        etInicio.doAfterTextChanged { validarFormulario() }
        etFin.doAfterTextChanged { validarFormulario() }
        etInfo.doAfterTextChanged { validarFormulario() }

        return view
    }

    private fun cargarZonasEnMapa() {
        val zonas = mapOf(
            "Zona 1" to listOf(
                LatLng(41.986500, -1.273800),
                LatLng(41.986500, -1.270600),
                LatLng(41.985200, -1.270600),
                LatLng(41.985200, -1.273800)
            ),
            "Zona 2" to listOf(
                LatLng(41.986500, -1.270600),
                LatLng(41.986500, -1.267400),
                LatLng(41.985200, -1.267400),
                LatLng(41.985200, -1.270600)
            ),
            "Zona 3" to listOf(
                LatLng(41.985200, -1.273800),
                LatLng(41.985200, -1.270600),
                LatLng(41.983900, -1.270600),
                LatLng(41.983900, -1.273800)
            ),
            "Zona 4" to listOf(
                LatLng(41.985200, -1.270600),
                LatLng(41.985200, -1.267400),
                LatLng(41.983900, -1.267400),
                LatLng(41.983900, -1.270600)
            )
        )

        zonas.forEach { (nombre, puntos) ->
            val polygon = googleMap.addPolygon(
                PolygonOptions()
                    .addAll(puntos)
                    .strokeColor(Color.parseColor("#2D75C9"))
                    .fillColor(Color.parseColor("#552D75C9")) // transparencia sobre tu color
                    .clickable(true)
            )
            polygon.tag = nombre
            zonasPoligonos[nombre] = polygon
        }

        googleMap.setOnPolygonClickListener { polygon ->
            poligonoSeleccionado?.fillColor = Color.parseColor("#552D75C9")
            polygon.fillColor = Color.parseColor("#883157B2") // un azul más intenso con opacidad 0x88
            poligonoSeleccionado = polygon
            zonaSeleccionada = polygon.tag.toString().substringAfter("Zona ").trim()
            Log.d("CorteFragment", "Zona seleccionada: $zonaSeleccionada")
            Toast.makeText(requireContext(), "Zona seleccionada: $zonaSeleccionada", Toast.LENGTH_SHORT).show()
            validarFormulario()
        }

        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isScrollGesturesEnabled = true
            isZoomGesturesEnabled = true
            isTiltGesturesEnabled = true
            isRotateGesturesEnabled = true
        }
    }

    private fun resaltarZonaSeleccionada(nombreZona: String) {
        val polygon = zonasPoligonos["Zona $nombreZona"] ?: return
        poligonoSeleccionado?.fillColor = Color.parseColor("#552196F3")
        polygon.fillColor = Color.parseColor("#AA0000FF")
        poligonoSeleccionado = polygon
    }

    private fun validarFormulario() {
        val inicio = etInicio.text.toString().trim()
        val fin = etFin.text.toString().trim()
        val info = etInfo.text.toString().trim()
        btnGuardar.isEnabled = zonaSeleccionada != null &&
                inicio.isNotEmpty() && fin.isNotEmpty() && info.isNotEmpty()
    }

    private fun guardarCorte() {
        val inicio = etInicio.text.toString().trim()
        val fin = etFin.text.toString().trim()
        val info = etInfo.text.toString().trim()

        if (zonaSeleccionada == null || inicio.isEmpty() || fin.isEmpty() || info.isEmpty()) {
            Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val formato = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

        try {
            val fechaInicio = formato.parse(inicio)
            val fechaFin = formato.parse(fin)

            if (fechaInicio != null && fechaFin != null && !fechaInicio.before(fechaFin)) {
                Toast.makeText(requireContext(), "La hora de inicio debe ser anterior a la de fin", Toast.LENGTH_LONG).show()
                return
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Formato de fecha no válido", Toast.LENGTH_SHORT).show()
            return
        }

        val dao = AppDatabase.getInstance(requireContext()).neighborDao()
        lifecycleScope.launch {
            val zoneId = zonaSeleccionada!!.toInt()
            val vecinos = dao.getNeighborsByZone(zoneId)
            Log.d("CorteFragment", "Se han encontrado ${vecinos.size} vecinos en la zona $zoneId")

            val dialog = AlertDialogFragment.newInstance(vecinos.size)
            dialog.show(parentFragmentManager, "alerta")

            parentFragmentManager.setFragmentResultListener("confirmacion_corte", viewLifecycleOwner) { _, bundle ->
                val confirmado = bundle.getBoolean("confirmado", false)
                if (confirmado) {
                    lifecycleScope.launch {
                        enviarCorreos(vecinos, inicio, fin, info)
                        guardarYCerrar(inicio, fin, info)
                    }
                }
            }
        }
    }


    private suspend fun enviarCorreos(
        vecinos: List<NeighborEntity>,
        inicio: String,
        fin: String,
        info: String
    ) {
        withContext(Dispatchers.IO) {
            for (vecino in vecinos) {
                try {
                    Log.d("CorteFragment", "Enviando correo a ${vecino.email}")
                    MailSender.send(
                        to = vecino.email,
                        subject = "Aviso de corte de agua",
                        body = """
                            Estimado/a ${vecino.name} ${vecino.surname},

                            Le informamos de un corte temporal de agua en su zona (${zonaSeleccionada}).

                            ⏱ *Horario previsto del corte:*
                            Desde: $inicio
                            Hasta: $fin

                            📄 *Motivo:* $info

                            Disculpe las molestias ocasionadas.

                            Atentamente,
                            Ayuntamiento de Sancho Abarca
                        """.trimIndent()
                    )
                } catch (e: Exception) {
                    Log.e("CorteFragment", "Error al enviar a ${vecino.email}", e)
                }
            }
        }
    }

    private fun guardarYCerrar(inicio: String, fin: String, info: String) {
        val dao = AppDatabase.getInstance(requireContext()).waterCutDao()
        lifecycleScope.launch {
            val nuevoCorte = WaterCutEntity(
                zone = zonaSeleccionada!!,
                startTime = inicio,
                endTime = fin,
                reason = info,
                idWorker = MainActivity.workerId ?: 0
            )
            dao.insertWaterCut(nuevoCorte)
            Toast.makeText(requireContext(), "Corte y correos enviados correctamente", Toast.LENGTH_LONG).show()
            findNavController().navigateUp()
        }
    }

    private fun configurarFechaHoraPickers() {
        val formato = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

        fun mostrarDialogo(et: EditText) {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, y, m, d ->
                calendar.set(Calendar.YEAR, y)
                calendar.set(Calendar.MONTH, m)
                calendar.set(Calendar.DAY_OF_MONTH, d)
                TimePickerDialog(requireContext(), { _, h, min ->
                    calendar.set(Calendar.HOUR_OF_DAY, h)
                    calendar.set(Calendar.MINUTE, min)
                    et.setText(formato.format(calendar.time))
                    validarFormulario()
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        etInicio.setOnClickListener { mostrarDialogo(etInicio) }
        etFin.setOnClickListener { mostrarDialogo(etFin) }


        etInicio.isFocusable = false
        etInicio.isClickable = true

        etFin.isFocusable = false
        etFin.isClickable = true

    }
}
