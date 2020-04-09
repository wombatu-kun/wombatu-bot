package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import wombatukun.bots.wombatubot.MockingUtils

class ReverseHandlerTest: MockingUtils() {

	private val reverseHandler = ReverseHandler();

	@Test
	fun testMan() {
		assertEquals("Реверс строки: реверс абырвалг", reverseHandler.man())
	}

	@Test
	fun testChannelMessageNotMatch() {
		assertFalse(reverseHandler.matches(buildMockUpdate("text", ChatType.channel)))
	}

	@Test
	fun testMessageTextNotMatch() {
		assertFalse(reverseHandler.matches(buildMockUpdate("реверс", ChatType.private)))
	}

	@Test
	fun testMessageTextMatch() {
		assertTrue(reverseHandler.matches(buildMockUpdate("реверс абырвалг", ChatType.private)))
	}

	@Test
	fun testHandle() {
		val response: SendMessage = reverseHandler.handle(buildMockUpdate("реверс абырвалг", ChatType.private))
		assertEquals("главрыба", response.text)
		assertEquals(CHAT_ID.toString(), response.chatId)
	}
}