package wombatukun.bots.wombatubot.config

import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.ApiContextInitializer
import javax.annotation.PostConstruct

@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = [ "wombatukun" ])
@ConfigurationProperties(prefix = "telegram")
class BotConfig {
	private val log = LoggerFactory.getLogger(BotConfig::class.qualifiedName)

	lateinit var botUsername: String
	lateinit var botToken: String
	lateinit var botAdmin: String

	@PostConstruct
	fun postConstruct() {
		ApiContextInitializer.init()
		log.info("BOT - {}, admin - {}", botUsername, botAdmin)
	}
}
