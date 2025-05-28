package tfg.selampr.hidrogestion.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

/**
 * Fragmento de diálogo que muestra una alerta cuando se confirma un corte de agua.
 *
 * Informa al usuario cuántos vecinos se verán afectados por el corte y le da la opción
 * de confirmar el envío de correos o cancelar la operación.
 *
 * La comunicación con el fragmento que lo invoca se realiza mediante setFragmentResult.
 */
class AlertDialogFragment : DialogFragment() {

    /**
     * Método principal que construye el diálogo al abrirse.
     * Recupera el número de vecinos desde los argumentos del fragmento y construye un AlertDialog.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Recuperamos el número de vecinos afectados pasado como argumento
        val vecinos = arguments?.getInt("vecinos") ?: 0

        // Construcción del cuadro de diálogo con botones "Aceptar" y "Cancelar"
        return AlertDialog.Builder(requireContext())
            .setTitle("ALERTA")
            .setMessage("Se verán afectados $vecinos vecinos. ¿Deseas notificarles por correo?")
            .setPositiveButton("Aceptar") { _, _ ->
                // Enviamos el resultado positivo al fragmento que abrió este diálogo
                setFragmentResult("confirmacion_corte", Bundle().apply {
                    putBoolean("confirmado", true)
                })
            }
            .setNegativeButton("Cancelar") { _, _ ->
                // Enviamos el resultado negativo
                setFragmentResult("confirmacion_corte", Bundle().apply {
                    putBoolean("confirmado", false)
                })
            }
            .create()
    }

    companion object {
        /**
         * Método de fábrica para crear una instancia del diálogo con el número de vecinos afectado.
         *
         * @param vecinos Número de vecinos que se verán afectados.
         * @return Una nueva instancia de [AlertDialogFragment] con los argumentos necesarios.
         */
        fun newInstance(vecinos: Int): AlertDialogFragment {
            return AlertDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt("vecinos", vecinos)
                }
            }
        }
    }
}
