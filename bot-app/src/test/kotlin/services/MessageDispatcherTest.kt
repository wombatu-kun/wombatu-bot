package wombatukun.bots.wombatubot.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.api.currency.CurrencyApi
import wombatukun.bots.wombatubot.MockingUtils
import wombatukun.bots.wombatubot.config.BotConfig

class MessageDispatcherTest: MockingUtils() {

	private val botConfig = mock(BotConfig::class.java)
	private val userService = mock(UserService::class.java)
	private val messageDispatcher = MessageDispatcherImpl(botConfig, userService, mock(CurrencyApi::class.java))

	private val MAN: String = """
		Текущий курс: руб
		Курс на дату: руб ДД.ММ.ГГГГ
		Фикс в rus-раскладку: рус ghbdtn
		Фикс в eng-раскладку: енг руддщ
		Анкапс: анкапс абЫрВАЛГ
		Количество символов: каунт абырвалг
		""".trimIndent()

	@Test
	fun testDispatchStart() {
		val update: Update = buildMockUpdate("/start", ChatType.private)
		val response: SendMessage? = messageDispatcher.dispatch(update)
		assertEquals(MAN, response?.text)
		assertEquals(CHAT_ID.toString(), response?.chatId)
		verify(userService, times(1)).upsertUser(update.message.from)
	}
}