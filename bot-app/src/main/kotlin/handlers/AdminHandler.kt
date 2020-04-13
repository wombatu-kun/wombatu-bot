package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse
import wombatukun.bots.wombatubot.services.UserService

class AdminHandler(
		private val botAdmin: String,
		private val userService: UserService
): MessageHandler {

	override fun man(): String {
		return ""
	}

	override fun matches(update: Update): Boolean {
		val msg: String? = update.message?.text
		return update.message.isUserMessage && msg != null
				&& msg.matches(Regex("^лист")) && update.message.from.id.toString() == botAdmin
	}

	override fun handle(update: Update): SendMessage {
		val text = userService.listUsers().sortedByDescending { it.lastTime }
				.map { "${it.userName} | ${it.firstName} ${it.lastName} | ${it.lastTime}" }.joinToString("\n")
		return buildSimpleResponse(update.message.chatId, text)
	}
}