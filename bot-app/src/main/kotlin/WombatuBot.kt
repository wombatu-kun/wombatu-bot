package wombatukun.bots.wombatubot

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import wombatukun.bots.wombatubot.config.BotConfig
import wombatukun.bots.wombatubot.services.MessageDispatcher

@SpringBootApplication
class WombatuBot(
		@Autowired private val botConfig: BotConfig,
		@Autowired private val messageDispatcher: MessageDispatcher
): TelegramLongPollingBot() {
	private val log = LoggerFactory.getLogger(WombatuBot::class.qualifiedName)

	override fun getBotUsername(): String {	return botConfig.botUsername }
	override fun getBotToken(): String { return botConfig.botToken }

	override fun onUpdateReceived(update: Update?) {
		if (update == null || update.message == null || update.message.from == null || update.message.from.bot) {
			return
		}
		log.info("received: {}", update)
		messageDispatcher.dispatch(update).forEach {
			it.text = escapeSpecialSymbols(it.text)
			sendResponse(it)
		}
	}

	/**
	 * messages with * ` [ _ in text aren't sending because of:
	 * TelegramApiRequestException: Error sending message: [400] Bad Request:
	 * can't parse entities: Can't find end of the entity starting at byte offset ..
	 */
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
}

fun main(args: Array<String>) {
	runApplication<WombatuBot>(*args)
}