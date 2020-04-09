package wombatukun.bots.wombatubot

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import wombatukun.bots.wombatubot.services.MessageDispatcher

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = [ "wombatukun" ])
@ConfigurationProperties(prefix = "telegram")
class WombatuBot(
		@Autowired private val messageDispatcher: MessageDispatcher
): TelegramLongPollingBot() {
	private val log = LoggerFactory.getLogger(WombatuBot::class.qualifiedName)

	private lateinit var botUsername: String
	private lateinit var botToken: String

	override fun onUpdateReceived(update: Update?) {
		if (update == null || update.message == null || update.message.from == null || update.message.from.bot) {
			return
		}
		log.info("received: {}", update)
		val msg: SendMessage? = messageDispatcher.dispatch(update)
		if (msg != null) {
			//messages with * ` [ _ in text aren't sending because of:
			//TelegramApiRequestException: Error sending message: [400] Bad Request:
			//can't parse entities: Can't find end of the entity starting at byte offset ..
			msg.setText(escapeSpecialSymbols(msg.text))
			sendResponse(msg)
		}
	}

	private fun escapeSpecialSymbols(text: String): String {
		return text.replace("*","\\*")
				.replace("`", "\\`")
				.replace("[", "\\[")
				.replace("_", "\\_")
	}

	private fun sendResponse(msg: SendMessage) {
		try {
			execute(msg)
		} catch (e: TelegramApiException) {
			log.error(e.toString())
		}
	}

	override fun getBotUsername(): String {	return botUsername }
	fun setBotUsername(name: String) { this.botUsername = name }
	override fun getBotToken(): String { return botToken }
	fun setBotToken(token: String) { this.botToken = token }
}

fun main(args: Array<String>) {
	ApiContextInitializer.init()
	runApplication<WombatuBot>(*args)
}