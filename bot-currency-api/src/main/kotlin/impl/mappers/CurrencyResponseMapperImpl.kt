package wombatukun.api.currency.impl.mappers

import org.springframework.stereotype.Component
import wombatukun.api.currency.CurrencyRequest
import wombatukun.api.currency.CurrencyResponse
import wombatukun.api.currency.ExchangeRate
import wombatukun.api.currency.impl.dto.ValCurs
import wombatukun.api.currency.impl.dto.Valute
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Component
class CurrencyResponseMapperImpl: CurrencyResponseMapper {

	private val FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy")

	override fun mapResponse(cbrResponse: ValCurs, request: CurrencyRequest): CurrencyResponse {
		val rates: List<ExchangeRate> = cbrResponse.valutes
				.filter { v -> request.codes.contains(v.charCode) }
				.map { v -> mapExchangeRate(v) }
				.toList()
		val date = if (cbrResponse.date != null) {
			LocalDate.parse(cbrResponse.date, FORMATTER)
		} else {
			request.date
		}
		return CurrencyResponse(date, rates)
	}

	override fun mapExchangeRate(valute: Valute): ExchangeRate {
		 val rate: Double = NumberFormat.getInstance(Locale.FRANCE).parse(valute.value ?: "0,0").toDouble()
		 return ExchangeRate(valute.charCode ?: "XXX", valute.nominal ?: 1, rate)
	}
}