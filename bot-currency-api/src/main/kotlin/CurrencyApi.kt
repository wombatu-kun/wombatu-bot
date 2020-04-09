package wombatukun.api.currency

interface CurrencyApi {

	fun getExchangeRates(request: CurrencyRequest): CurrencyResponse

}