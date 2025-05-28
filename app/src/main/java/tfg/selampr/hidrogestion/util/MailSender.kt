package tfg.selampr.hidrogestion.utils

import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object MailSender {

    private const val USERNAME = "hidrogestionmail@gmail.com" // <-- tu correo GMAIL
    private const val PASSWORD = "skqe mzmr fcyq vveq" // <-- contraseña de aplicación, no la de tu cuenta

    fun send(to: String, subject: String, body: String) {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587")
        }

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(USERNAME, PASSWORD)
            }
        })

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(USERNAME))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
                setSubject(subject)
                setText(body)
            }

            Transport.send(message)

        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}
