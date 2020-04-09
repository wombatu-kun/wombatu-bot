package wombatukun.api.currency

import java.time.LocalDate

data class CurrencyRequest (
		val date: LocalDate = LocalDate.now(),
		val codes: List<String> = listOf("USD", "EUR")
)