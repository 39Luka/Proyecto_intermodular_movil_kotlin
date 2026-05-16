package net.iesochoa.silvia.projecto_intermodular.model

data class HorizontalCardItem(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val leftLabel: String? = null,
    val leftValue: String? = null,
    val rightLabel: String? = null,
    val rightValue: String? = null,
    val imageUrl: String? = null,
    val imageRes: Int? = null,
    val categoryName: String = "Obrador",
    val isUsed: Boolean = false
)
