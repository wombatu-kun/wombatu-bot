package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import wombatukun.bots.wombatubot.MockingUtils

class CountHandlerTest: MockingUtils() {

	private val countHandler = CountHandler();

	@Test
	fun testMan() {
		assertEquals("Количество символов: каунт абырвалг", countHandler.man())
	}

	@Test
	fun testSuperGroupMessageNotMatches() {
		assertFalse(countHandler.matches(buildMockUpdate("text", ChatType.supergroup)))
	}

	@Test
	fun testMessageTextNotMatches() {
		assertFalse(countHandler.matches(buildMockUpdate("каунт", ChatType.private)))
	}

	@Test
	fun testMessageTextMatches() {
		assertTrue(countHandler.matches(buildMockUpdate("каунт абырвалг", ChatType.private)))
	}

	@Test
	fun testHandle() {
		val responses = countHandler.handle(buildMockUpdate("каунт абырвалг", ChatType.private))
		assertEquals(1, responses.size)
		assertEquals("8", responses[0].text)
		assertEquals(CHAT_ID.toString(), responses[0].chatId)
	}
}