package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import wombatukun.bots.wombatubot.MockingUtils

class DummyHandlerTest: MockingUtils() {

	private val dummyHandler = DummyHandler();

	@Test
	fun testMan() {
		assertEquals("", dummyHandler.man())
	}

	@Test
	fun testGroupMessageNotMatch() {
		assertFalse(dummyHandler.matches(buildMockUpdate("text", ChatType.group)))
	}

	@Test
	fun testMessageTextMatch() {
		assertTrue(dummyHandler.matches(buildMockUpdate("абырвалг", ChatType.private)))
	}

	@Test
	fun testHandle() {
		val response: SendMessage = dummyHandler.handle(buildMockUpdate("абырвалг", ChatType.private))
		assertEquals("so, you say: абырвалг", response.text)
		assertEquals(CHAT_ID.toString(), response.chatId)
	}
}