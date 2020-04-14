package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.dao.entities.User
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
		return update.message.isUserMessage
				&& msg != null
				&& msg.matches(Regex("(^лист)|(^мсг \\d{1,12} .+)"))
				&& update.message.from.id == botAdmin.toInt()
	}

	override fun handle(update: Update): SendMessage {
		val msg: String = update.message.text
		when {
			msg == "лист" -> {
				val text = userService.listUsers().sortedByDescending { it.lastTime }
						.map { "${it.id} | ${it.lastTime} | ${it.userName}" }.joinToString("\n")
				return buildSimpleResponse(update.message.chatId, text)
			}
			msg.matches(Regex("^мсг \\d{1,12} .+")) -> try {
				val userId = msg.split(" ")[1].toLong()
				val text = msg.substringAfter(userId.toString()).trim()
				val user: User = userService.findById(userId)
				return buildSimpleResponse(user.id!!, text)
			} catch (e: Exception) {}
		}
		return buildSimpleResponse(update.message.chatId, "херню написал!")
	}
}