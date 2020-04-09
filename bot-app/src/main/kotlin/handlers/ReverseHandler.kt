package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse

class ReverseHandler: MessageHandler {

	override fun man(): String {
		return "Реверс строки: реверс абырвалг"
	}

	override fun matches(update: Update): Boolean {
		val msg: String? = update.message?.text
		return update.message.isUserMessage && msg != null && msg.matches(Regex("^реверс .+"))
	}

	override fun handle(update: Update): SendMessage {
		return buildSimpleResponse(update.message.chatId,
				update.message.text.substringAfter(' ').reversed())
	}
}