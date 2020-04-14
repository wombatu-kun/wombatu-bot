package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
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
		val responses = dummyHandler.handle(buildMockUpdate("абырвалг", ChatType.private))
		assertEquals(1, responses.size)
		assertEquals("so, you say: абырвалг", responses[0].text)
		assertEquals(CHAT_ID.toString(), responses[0].chatId)
	}
}