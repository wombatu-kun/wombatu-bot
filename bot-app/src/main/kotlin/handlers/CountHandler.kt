package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse

class CountHandler: MessageHandler {

	override fun man(): String {
		return "Количество символов: каунт абырвалг"
	}

	override fun matches(update: Update): Boolean {
		val msg: String? = update.message?.text
		return update.message.isUserMessage && msg != null && msg.matches(Regex("^каунт .+"))
	}

	override fun handle(update: Update): SendMessage {
		return buildSimpleResponse(update.message.chatId,
				update.message.text.substringAfter(' ').length.toString())
	}
}