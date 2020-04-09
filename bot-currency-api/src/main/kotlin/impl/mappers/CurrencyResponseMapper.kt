package wombatukun.api.currency.impl.mappers

import wombatukun.api.currency.CurrencyRequest
import wombatukun.api.currency.CurrencyResponse
import wombatukun.api.currency.ExchangeRate
import wombatukun.api.currency.impl.dto.ValCurs
import wombatukun.api.currency.impl.dto.Valute

interface CurrencyResponseMapper {

	fun mapResponse(cbrResponse: ValCurs, request: CurrencyRequest): CurrencyResponse
	fun mapExchangeRate(valute: Valute): ExchangeRate

}