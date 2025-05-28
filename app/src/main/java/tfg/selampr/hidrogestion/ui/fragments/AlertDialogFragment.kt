package tfg.selampr.hidrogestion.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener

class AlertDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val vecinos = arguments?.getInt("vecinos") ?: 0

        return AlertDialog.Builder(requireContext())
            .setTitle("ALERTA")
            .setMessage("Se verán afectados $vecinos vecinos. ¿Deseas notificarles por correo?")
            .setPositiveButton("Aceptar") { _, _ ->
                setFragmentResult("confirmacion_corte", Bundle().apply {
                    putBoolean("confirmado", true)
                })
            }
            .setNegativeButton("Cancelar") { _, _ ->
                setFragmentResult("confirmacion_corte", Bundle().apply {
                    putBoolean("confirmado", false)
                })
            }
            .create()
    }

    companion object {
        fun newInstance(vecinos: Int): AlertDialogFragment {
            return AlertDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt("vecinos", vecinos)
                }
            }
        }
    }


}
