package wombatukun.api.currency.impl.config

import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.impl.client.HttpClients
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import javax.annotation.PostConstruct

@Configuration
@EnableConfigurationProperties
@EnableCircuitBreaker
@ConfigurationProperties(prefix = "apis.cbr")
class CbrConfig {
	private val log = LoggerFactory.getLogger(CbrConfig::class.qualifiedName)

	lateinit var url: String

	@PostConstruct
	fun postConstruct() {
		log.info("Currency-api url: {}", url)
	}

	@Bean
	fun cbrRestTemplate(): RestTemplate {
		val httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier()).build()
		val requestFactory = HttpComponentsClientHttpRequestFactory()
		requestFactory.httpClient = httpClient
		return RestTemplate(requestFactory)
	}
}