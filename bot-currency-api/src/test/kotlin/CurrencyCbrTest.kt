package wombatukun.api.currency.impl

import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.junit4.SpringRunner
import wombatukun.api.currency.CurrencyRequest
import wombatukun.api.currency.CurrencyResponse
import java.time.LocalDate

@RunWith(SpringRunner::class)
@SpringBootTest
@SpringBootApplication
@PropertySource("classpath:cbr-test.properties")
class CurrencyCbrTest {

	@Autowired
	lateinit var currencyApi: CurrencyCbr

	@Test
	fun testApiInjected() {
		assertNotNull(currencyApi)
	}

	@Test
	fun testGetExchangeRates() {
		val response: CurrencyResponse = currencyApi.getExchangeRates(CurrencyRequest(LocalDate.parse("2009-09-09")))
		println(response)
		assertEquals(LocalDate.parse("2009-09-09"), response.date)
		assertEquals(2, response.rates.size)
		assertEquals("USD", response.rates[0].code)
		assertEquals(1, response.rates[0].nominal)
		assertEquals(31.3754, response.rates[0].rate, 0.01)
		assertEquals("EUR", response.rates[1].code)
		assertEquals(1, response.rates[1].nominal)
		assertEquals(45.1084, response.rates[1].rate, 0.01)
	}
}