package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse

class TransmitterHandler(
		private val botAdmin: String
): MessageHandler {

	override fun man(): String {
		return ""
	}

	override fun matches(update: Update): Boolean {
		return update.message.isUserMessage && update.message?.text != null
	}

	override fun handle(update: Update): List<SendMessage> {
		val from = update.message.from
		if (from.id.toString() != botAdmin) {
			val text = "${from.id} (@${from.userName}): ${update.message.text}"
			return listOf(buildSimpleResponse(botAdmin.toLong(), text))
		} else {
			return emptyList()
		}
	}
}