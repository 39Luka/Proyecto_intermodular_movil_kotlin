package net.iesochoa.silvia.projecto_intermodular.ui.components


data class HorizontalCardItem(
    val title: String,
    val description: String = "",
    val leftLabel: String? = null,
    val leftValue: String? = null,
    val rightLabel: String? = null,
    val rightValue: String? = null,
    val imageRes: Int? = null
)
