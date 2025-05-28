package tfg.selampr.hidrogestion.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import tfg.selampr.hidrogestion.R
import tfg.selampr.hidrogestion.ui.activities.LoginActivity
import tfg.selampr.hidrogestion.ui.activities.MainActivity

class PerfilFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        val nameText = view.findViewById<TextView>(R.id.nameUser)
        val emailText = view.findViewById<TextView>(R.id.emailUser)
        val roleText = view.findViewById<TextView>(R.id.roleUser)
        val timeText = view.findViewById<TextView>(R.id.accessTime)

        val logoutButton = view.findViewById<Button>(R.id.logoutButton)


        nameText.text = MainActivity.workerName ?: "Usuario"
        emailText.text = MainActivity.workerEmail ?: "Email"
        roleText.text = MainActivity.workerRole ?: "Rol"
        timeText.text = "Último inicio sesión: ${MainActivity.loginTime ?: "Desconocido"}"


        logoutButton.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }


}
