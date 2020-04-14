package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse

class StartHandler(
		private val handlers: List<MessageHandler>
): MessageHandler {

	override fun man(): String {
		return ""
	}

	override fun matches(update: Update): Boolean {
		val text = update.message?.text
		return update.message.isUserMessage &&  text != null && (text == "ман" || text == "/start")
	}

	override fun handle(update: Update): List<SendMessage> {
		return listOf(buildSimpleResponse(update.message.chatId,
				handlers.map { it.man() }.filter { it.isNotBlank() }.joinToString("\n")))
	}
}