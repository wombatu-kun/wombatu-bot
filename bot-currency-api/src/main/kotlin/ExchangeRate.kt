package wombatukun.api.currency

data class ExchangeRate (
	val code: String = "XXX",
	val nominal: Int = 1,
	val rate: Double = 0.0
)