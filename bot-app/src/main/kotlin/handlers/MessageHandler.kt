package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.time.format.DateTimeFormatter

interface MessageHandler {

	fun man(): String //user manual
	fun matches(update: Update): Boolean //trigger
	fun handle(update: Update): List<SendMessage> //action and list of responses

	companion object {
		val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

		fun buildSimpleResponse(chatId: Long, text: String): SendMessage {
			val response = SendMessage()
			response.enableMarkdown(true)
			response.setChatId(chatId)
			response.text = text
			return response;
		}
	}
}