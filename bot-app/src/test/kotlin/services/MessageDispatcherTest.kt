package wombatukun.bots.wombatubot.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.api.currency.CurrencyApi
import wombatukun.bots.wombatubot.MockingUtils

class MessageDispatcherTest: MockingUtils() {

	private val userService = Mockito.mock(UserService::class.java)
	private val messageDispatcher = MessageDispatcherImpl(userService, Mockito.mock(CurrencyApi::class.java))

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
		val update: Update = buildMockUpdate("/start", ChatType.private)
		val response: SendMessage? = messageDispatcher.dispatch(update)
		assertEquals(MAN, response?.text)
		assertEquals(CHAT_ID.toString(), response?.chatId)
		verify(userService, times(1)).upsertUser(update.message.from)
	}

	@Test
	fun testDispatchMan() {
		val update: Update = buildMockUpdate("ман", ChatType.private)
		val response: SendMessage? = messageDispatcher.dispatch(update)
		assertEquals(MAN, response?.text)
		assertEquals(CHAT_ID.toString(), response?.chatId)
		verify(userService, times(1)).upsertUser(update.message.from)
	}
}