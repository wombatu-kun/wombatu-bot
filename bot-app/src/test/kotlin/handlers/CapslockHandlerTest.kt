package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import wombatukun.bots.wombatubot.MockingUtils

class CapslockHandlerTest: MockingUtils() {

	private val capslockHandler = CapslockHandler();

	@Test
	fun testMan() {
		assertEquals("Анкапс: анкапс абЫрВАЛГ", capslockHandler.man())
	}

	@Test
	fun testGroupMessageNotMatch() {
		assertFalse(capslockHandler.matches(buildMockUpdate("text", ChatType.group)))
	}

	@Test
	fun testMessageTextNotMatch() {
		assertFalse(capslockHandler.matches(buildMockUpdate("анкап", ChatType.private)))
	}

	@Test
	fun testMessageTextMatch() {
		assertTrue(capslockHandler.matches(buildMockUpdate("анкапс абЫрВАЛГ", ChatType.private)))
	}

	@Test
	fun testHandle() {
		val response: SendMessage = capslockHandler.handle(buildMockUpdate("анкапс абЫрВАЛГ", ChatType.private))
		assertEquals("АБыРвалг", response.text)
		assertEquals(CHAT_ID.toString(), response.chatId)
	}
}