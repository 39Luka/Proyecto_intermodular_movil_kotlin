package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.remember
import coil.compose.AsyncImage
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.utils.decodeBase64ToBitmap

/**
 * Componente de imagen asíncrona personalizado para mantener la consistencia
 * visual de las imágenes cargadas desde red en toda la aplicación.
 */
/**
 * Componente de imagen asíncrona basado en Coil.
 * Proporciona consistencia visual al cargar imágenes desde una URL o Base64,
 * incluyendo una imagen de marcador de posición (placeholder) y de error.
 *
 * @param model Fuente de la imagen (URL, String Base64, Int Resource, etc.).
 * @param contentDescription Texto descriptivo para accesibilidad.
 * @param modifier Modificador de Compose para tamaño, forma, etc.
 * @param contentScale Modo de escalado de la imagen.
 * @param alpha Nivel de transparencia.
 */
@Composable
fun AppAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = 1f
) {
    // Si el modelo es un String, aplicamos lógica de pre-procesamiento
    val processedModel = remember(model) {
        if (model is String) {
            when {
                // Caso 1: Es una imagen Base64 (con o sin prefijo)
                model.startsWith("data:image") || model.length > 100 -> {
                    model.decodeBase64ToBitmap()
                }
                // Caso 2: Es una URL completa
                model.startsWith("http") -> {
                    model
                }
                // Caso 3: Es una ruta relativa de la API (ej: uploads/imagen.jpg)
                model.isNotEmpty() -> {
                    val baseUrl = "https://proyectointermodularapi-production.up.railway.app/"
                    // Evitamos duplicar barras si el modelo ya empieza por /
                    if (model.startsWith("/")) {
                        baseUrl.removeSuffix("/") + model
                    } else {
                        baseUrl + model
                    }
                }
                else -> model
            }
        } else {
            model
        }
    }

    AsyncImage(
        model = processedModel ?: R.drawable.croissant,
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.croissant),
        error = painterResource(id = R.drawable.croissant),
        modifier = modifier,
        contentScale = contentScale,
        alpha = alpha
    )
}
