package wombatukun.api.currency

import java.time.LocalDate

data class CurrencyResponse (
		val date: LocalDate,
		val rates: List<ExchangeRate>
)