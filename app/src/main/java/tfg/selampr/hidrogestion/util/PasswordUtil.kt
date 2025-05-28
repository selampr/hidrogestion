package tfg.selampr.hidrogestion.util

import java.security.MessageDigest

/**
 * Función que convierte una contraseña en texto plano a un hash seguro usando SHA-256.
 *
 * Esta técnica permite almacenar contraseñas de forma segura sin guardarlas como texto legible.
 * El resultado es un string hexadecimal irreconocible, útil para comparación segura en login.
 *
 * @param password Contraseña original introducida por el usuario.
 * @return Cadena de texto hexadecimal que representa el hash SHA-256 de la contraseña.
 */
fun hashPassword(password: String): String {
    // Convierte la contraseña en un array de bytes
    val bytes = password.toByteArray()

    // Obtiene una instancia del algoritmo SHA-256
    val md = MessageDigest.getInstance("SHA-256")

    // Calcula el hash de los bytes
    val digest = md.digest(bytes)

    // Convierte el resultado a hexadecimal (dos caracteres por byte)
    return digest.joinToString("") { "%02x".format(it) }
}
