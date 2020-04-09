package wombatukun.bots.wombatubot.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import wombatukun.api.currency.CurrencyApi
import wombatukun.bots.wombatubot.MockingUtils

class MessageDispatcherTest: MockingUtils() {

	private val messageDispatcher = MessageDispatcherImpl(Mockito.mock(CurrencyApi::class.java))

	private val MAN: String = """
		Текущий курс: руб
		Курс на дату: руб ДД.ММ.ГГГГ
		Фикс в rus-раскладку: рус ghbdtn
		Фикс в eng-раскладку: енг руддщ
		Анкапс: анкапс абЫрВАЛГ
		Количество символов: каунт абырвалг
		Реверс строки: реверс абырвалг
		""".trimIndent().plus("\n")

	@Test
	fun testDispatchStart() {
		val response: SendMessage? = messageDispatcher.dispatch(buildMockUpdate("/start", ChatType.private))
		assertEquals(MAN, response?.text)
		assertEquals(CHAT_ID.toString(), response?.chatId)
	}

	@Test
	fun testDispatchMan() {
		val response: SendMessage? = messageDispatcher.dispatch(buildMockUpdate("ман", ChatType.private))
		assertEquals(MAN, response?.text)
		assertEquals(CHAT_ID.toString(), response?.chatId)
	}
}