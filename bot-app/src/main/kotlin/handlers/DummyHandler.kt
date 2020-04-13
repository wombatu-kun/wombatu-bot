package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse

class DummyHandler: MessageHandler {

	override fun man(): String {
		return ""
	}

	override fun matches(update: Update): Boolean {
		return update.message.isUserMessage && update.message?.text != null
	}

	override fun handle(update: Update): SendMessage {
		val sender = update.message.from
		val msg: String = update.message.text
		val text = when {
			msg.startsWith("кто ты",true) -> "я есть вомбату-куна вомбату-бот!"
			msg.startsWith("кто я", true) -> "ты есть то, что обозначается как @${sender.userName}"
			else -> "so, you say: ${msg}"
		}
		return buildSimpleResponse(update.message.chatId, text)
	}
}