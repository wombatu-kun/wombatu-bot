package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse

class FixKeyboardHandler: MessageHandler {

	private val rus = ".,юЮбБьЬтТиИмМсСчЧяЯжЖдДлЛоОрРпПаАвВыЫфФъЪхХзЗщЩшШгГнНеЕкКуУцЦйЙёЁэЭ\"№;:?"
	private val eng = "/?.>,<mMnNbBvVcCxXzZ;:lLkKjJhHgGfFdDsSaA]}[{pPoOiIuUyYtTrReEwWqQ`~'\"@#$^&"

	override fun man(): String {
		return """
			Фикс в rus-раскладку: рус ghbdtn
			Фикс в eng-раскладку: енг руддщ
		""".trimIndent()
	}

	override fun matches(update: Update): Boolean {
		val msg: String? = update.message?.text
		return update.message.isUserMessage && msg != null && msg.matches(Regex("(^рус .+)|(^енг .+)"))
	}

	override fun handle(update: Update): SendMessage {
		val msg = update.message.text
		val text = when {
			msg.startsWith("рус ") ->
				msg.substringAfter(' ').map { exchangeSymbol(it, eng, rus) }.joinToString("")
			msg.startsWith("енг ") ->
				msg.substringAfter(' ').map { exchangeSymbol(it, rus, eng) }.joinToString("")
			else -> man()
		}
		return buildSimpleResponse(update.message.chatId, text)
	}

	private fun exchangeSymbol(c: Char, layoutFrom: String, layoutTo: String): Char {
		val idx = layoutFrom.indexOf(c)
		return if (idx == -1) c else layoutTo[idx]
	}
}