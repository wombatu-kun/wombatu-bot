package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse

class CapslockHandler: MessageHandler {

	override fun man(): String {
		return "Анкапс: анкапс абЫрВАЛГ"
	}

	override fun matches(update: Update): Boolean {
		val msg: String? = update.message?.text
		return update.message.isUserMessage && msg != null && msg.matches(Regex("^анкапс .+"))
	}

	override fun handle(update: Update): SendMessage {
		val text = update.message.text
				.substringAfter(' ')
				.map { if (it.isUpperCase()) it.toLowerCase() else it.toUpperCase() }
				.joinToString("")
		return buildSimpleResponse(update.message.chatId, text)
	}
}