package net.iesochoa.silvia.projecto_intermodular.utils

import android.util.Base64
import net.iesochoa.silvia.projecto_intermodular.data.User
import org.json.JSONObject

object JwtUtils {
    fun decodeUser(token: String, fallbackEmail: String = ""): User {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return User(email = fallbackEmail)

            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            val json = JSONObject(payload)

            val email = json.optString("sub", json.optString("email", fallbackEmail))
            val role = json.optString("role", json.optJSONArray("roles")?.optString(0) ?: "USER")
            val id = json.optInt("userId", json.optInt("id", 0))

            User(id = id, email = email, name = email.substringBefore("@"), role = role)
        } catch (e: Exception) {
            User(email = fallbackEmail)
        }
    }
}
