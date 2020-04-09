package wombatukun.api.currency.impl

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import wombatukun.api.currency.CurrencyApi
import wombatukun.api.currency.CurrencyRequest
import wombatukun.api.currency.CurrencyResponse
import wombatukun.api.currency.impl.config.CbrConfig
import wombatukun.api.currency.impl.dto.ValCurs
import wombatukun.api.currency.impl.mappers.CurrencyResponseMapper
import java.time.format.DateTimeFormatter

@Service
@PropertySource("classpath:currency-api.properties")
class CurrencyCbr(
		@Autowired val cbrConfig: CbrConfig,
		@Autowired val cbrRestTemplate: RestTemplate,
		@Autowired val currencyResponseMapper: CurrencyResponseMapper
): CurrencyApi {
	private val log = LoggerFactory.getLogger(CurrencyCbr::class.qualifiedName)

	private val FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy")

	@HystrixCommand(fallbackMethod = "buildFailureResponse", threadPoolKey = "currencyThreadPool")
	override fun getExchangeRates(request: CurrencyRequest): CurrencyResponse {
		log.debug("Request: {}", request)
		val cbrResponse = cbrRestTemplate.getForObject(cbrConfig.url + request.date.format(FORMATTER), ValCurs::class.java)
		log.debug("Response: {}", cbrResponse)
		return currencyResponseMapper.mapResponse(cbrResponse!!, request)
	}

	private fun buildFailureResponse(request: CurrencyRequest): CurrencyResponse {
		return CurrencyResponse(request.date, emptyList())
	}
}