package tfg.selampr.hidrogestion.utils

import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/**
 * Objeto singleton que gestiona el envío de correos electrónicos usando el servidor SMTP de Gmail.
 *
 * Se utiliza principalmente para notificar a los vecinos sobre cortes de agua desde el CorteFragment.
 */
object MailSender {

    // Dirección de correo utilizada como remitente
    private const val USERNAME = "hidrogestionmail@gmail.com"

    // Contraseña de aplicación generada desde la cuenta de Google (no la contraseña de acceso)
    private const val PASSWORD = "skqe mzmr fcyq vveq"

    /**
     * Envía un correo electrónico simple (texto plano) al destinatario especificado.
     *
     * @param to Dirección de correo del destinatario.
     * @param subject Asunto del correo.
     * @param body Cuerpo del mensaje en texto plano.
     */
    fun send(to: String, subject: String, body: String) {
        // Configuración de propiedades para el servidor SMTP de Gmail
        val props = Properties().apply {
            put("mail.smtp.auth", "true") // Requiere autenticación
            put("mail.smtp.starttls.enable", "true") // Activa TLS para seguridad
            put("mail.smtp.host", "smtp.gmail.com") // Servidor SMTP de Gmail
            put("mail.smtp.port", "587") // Puerto para TLS
        }

        // Crea una sesión autenticada con usuario y contraseña
        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(USERNAME, PASSWORD)
            }
        })

        try {
            // Crea el mensaje de correo
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(USERNAME)) // Remitente
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)) // Destinatario
                setSubject(subject) // Asunto
                setText(body) // Cuerpo del mensaje (texto plano)
            }

            // Envía el mensaje
            Transport.send(message)

        } catch (e: MessagingException) {
            // Captura y muestra cualquier error que ocurra al enviar el correo
            e.printStackTrace()
        }
    }
}
