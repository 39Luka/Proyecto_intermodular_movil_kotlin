package net.iesochoa.silvia.projecto_intermodular.ui.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

/**
 * Decodifica una cadena de texto en formato Base64 a un objeto [Bitmap] de Android.
 * Soporta cadenas con prefijo (ej: data:image/png;base64,...) o solo el contenido Base64.
 * Limpia automáticamente espacios en blanco y saltos de línea.
 * @return El Bitmap decodificado o null si la cadena no es válida o hay un error.
 */
fun String.decodeBase64ToBitmap(): Bitmap? {
    return try {
        // Limpiamos la cadena de posibles espacios, saltos de línea y retornos de carro
        val cleanedString = this.trim().replace("\n", "").replace("\r", "").replace(" ", "")
        
        // Si contiene el prefijo data:image/..., lo eliminamos antes de decodificar
        // Usamos substringAfterLast para manejar casos donde el prefijo pueda estar duplicado
        val base64Data = if (cleanedString.contains(",")) {
            cleanedString.substringAfterLast(",")
        } else {
            cleanedString
        }
        
        val decodedBytes = Base64.decode(base64Data, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        null
    }
}
