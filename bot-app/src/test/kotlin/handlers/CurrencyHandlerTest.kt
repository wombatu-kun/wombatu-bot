package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import wombatukun.api.currency.CurrencyApi
import wombatukun.api.currency.CurrencyRequest
import wombatukun.api.currency.CurrencyResponse
import wombatukun.api.currency.ExchangeRate
import wombatukun.bots.wombatubot.MockingUtils
import java.time.LocalDate

class CurrencyHandlerTest: MockingUtils() {

	private val currencyApi = Mockito.mock(CurrencyApi::class.java)
	private val currencyHandler = CurrencyHandler(currencyApi)

	@Test
	fun testMan() {
		assertEquals("Текущий курс: руб\nКурс на дату: руб ДД.ММ.ГГГГ", currencyHandler.man())
	}

	@Test
	fun testChannelMessageNotMatches() {
		assertFalse(currencyHandler.matches(buildMockUpdate("руб", ChatType.channel)))
	}

	@Test
	fun testMessageTextMatches() {
		assertTrue(currencyHandler.matches(buildMockUpdate("руб", ChatType.private)))
	}

	@Test
	fun testHandleWithoutDate() {
		`when`(currencyApi.getExchangeRates(CurrencyRequest())).thenReturn(
				CurrencyResponse(LocalDate.now(), listOf(ExchangeRate("USD", 1, 80.0), ExchangeRate("EUR", 1, 90.0))))
		val responses = currencyHandler.handle(buildMockUpdate("руб", ChatType.private))
		assertEquals(1, responses.size)
		assertEquals("${LocalDate.now().format(MessageHandler.formatter)}: $ = 80.0₽, € = 90.0₽", responses[0].text)
		assertEquals(CHAT_ID.toString(), responses[0].chatId)
	}

	@Test
	fun testHandleWithDate() {
		`when`(currencyApi.getExchangeRates(CurrencyRequest(LocalDate.parse("2020-04-01")))).thenReturn(
				CurrencyResponse(LocalDate.parse("2020-04-01"), listOf(ExchangeRate("USD", 1, 80.0), ExchangeRate("EUR", 1, 90.0))))
		val responses = currencyHandler.handle(buildMockUpdate("руб 01.04.2020", ChatType.private))
		assertEquals(1, responses.size)
		assertEquals("01.04.2020: $ = 80.0₽, € = 90.0₽", responses[0].text)
		assertEquals(CHAT_ID.toString(), responses[0].chatId)
	}

	@Test
	fun testHandleWithNonDate() {
		val responses = currencyHandler.handle(buildMockUpdate("руб 31.13.2020", ChatType.private))
		assertEquals("Текущий курс: руб\nКурс на дату: руб ДД.ММ.ГГГГ", responses[0].text)
		assertEquals(CHAT_ID.toString(), responses[0].chatId)
	}

	@Test
	fun testHandleEmptyResponse() {
		`when`(currencyApi.getExchangeRates(CurrencyRequest(LocalDate.parse("2020-04-26")))).thenReturn(
				CurrencyResponse(LocalDate.parse("2020-04-26"), emptyList()))
		val responses = currencyHandler.handle(buildMockUpdate("руб 26.04.2020", ChatType.private))
		assertEquals("26.04.2020: не удалось получить курсы валют", responses[0].text)
		assertEquals(CHAT_ID.toString(), responses[0].chatId)
	}
}