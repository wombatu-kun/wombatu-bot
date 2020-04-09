package wombatukun.api.currency.impl.mappers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import wombatukun.api.currency.CurrencyRequest
import wombatukun.api.currency.impl.dto.ValCurs
import wombatukun.api.currency.impl.dto.Valute
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CurrencyResponseMapperTest {

	private val currencyResponseMapper = CurrencyResponseMapperImpl()

	@Test
	fun testMapNullsExchangeRate() {
		val valute = Valute(null, null, null)
		val exchangeRate = currencyResponseMapper.mapExchangeRate(valute)
		assertEquals("XXX", exchangeRate.code)
		assertEquals(1, exchangeRate.nominal)
		assertEquals(0.0, exchangeRate.rate, 0.001)
	}

	@Test
	fun testMapExchangeRate() {
		val valute = Valute("GBP", 1, "93,33")
		val exchangeRate = currencyResponseMapper.mapExchangeRate(valute)
		assertEquals("GBP", exchangeRate.code)
		assertEquals(1, exchangeRate.nominal)
		assertEquals(93.33, exchangeRate.rate, 0.001)
	}

	@Test
	fun testMapEmptyValutes() {
		val request = CurrencyRequest(LocalDate.now())
		val cbrResponse = ValCurs()
		val response = currencyResponseMapper.mapResponse(cbrResponse, request)
		assertEquals(request.date, response.date)
		assertTrue(response.rates.isEmpty())
	}

	@Test
	fun testMapOneValute() {
		val request = CurrencyRequest(LocalDate.now())
		val valute = Valute("USD", 1, "23,23")
		val cbrResponse = ValCurs(request.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), listOf(valute))
		val response = currencyResponseMapper.mapResponse(cbrResponse, request)
		assertEquals(request.date, response.date)
		assertEquals(1, response.rates.size)
		assertEquals(valute.charCode, response.rates[0].code)
		assertEquals(valute.nominal, response.rates[0].nominal)
		assertEquals(23.23, response.rates[0].rate, 0.001)
	}

	@Test
	fun testMapManyValutes() {
		val request = CurrencyRequest(LocalDate.now())
		val valute1 = Valute("USD", 1, "23,23")
		val valute2 = Valute("YYY", 1, "14,88")
		val valute3 = Valute("EUR", 1, "42,42")
		val cbrResponse = ValCurs(request.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), listOf(valute1, valute2, valute3))
		val response = currencyResponseMapper.mapResponse(cbrResponse, request)
		assertEquals(request.date, response.date)
		assertEquals(2, response.rates.size)
		assertEquals(valute1.charCode, response.rates[0].code)
		assertEquals(valute1.nominal, response.rates[0].nominal)
		assertEquals(23.23, response.rates[0].rate, 0.001)
		assertEquals(valute3.charCode, response.rates[1].code)
		assertEquals(valute3.nominal, response.rates[1].nominal)
		assertEquals(42.42, response.rates[1].rate, 0.001)
	}
}