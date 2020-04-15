package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse
import wombatukun.bots.wombatubot.services.UserService
import java.time.format.DateTimeFormatter

class AdminHandler(
		private val botAdmin: String,
		private val userService: UserService
): MessageHandler {

	private val dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

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

	override fun handle(update: Update): List<SendMessage> {
		val msg: String = update.message.text
		when {
			msg == "лист" -> {
				val text = userService.listUsers().sortedByDescending { it.lastTime }
						.map { "${it.id} | ${it.lastTime.format(dtFormat)} | @${it.userName}" }
						.joinToString("\n")
				return listOf(buildSimpleResponse(update.message.chatId, text))
			}
			msg.matches(Regex("^мсг \\d{1,12} .+")) -> try {
				val userId = msg.split(" ")[1].toLong()
				val user = userService.findById(userId)
				return listOf(buildSimpleResponse(user.id!!, msg.substringAfter(userId.toString()).trim()))
			} catch (e: Exception) {}
		}
		return listOf(buildSimpleResponse(update.message.chatId, "херню написал!"))
	}
}