package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import wombatukun.bots.wombatubot.MockingUtils

class TransmitterHandlerTest: MockingUtils() {

	private val transmitterHandler = TransmitterHandler("2")

	@Test
	fun testMan() {
		assertEquals("", transmitterHandler.man())
	}

	@Test
	fun testGroupMessageNotMatch() {
		assertFalse(transmitterHandler.matches(buildMockUpdate("text", ChatType.group)))
	}

	@Test
	fun testMessageTextMatch() {
		assertTrue(transmitterHandler.matches(buildMockUpdate("абырвалг", ChatType.private)))
	}

	@Test
	fun testHandle() {
		val responses = transmitterHandler.handle(buildMockUpdate("абырвалг", ChatType.private))
		assertEquals(1, responses.size)
		assertEquals("1 (@username): абырвалг", responses[0].text)
		assertEquals("2", responses[0].chatId)
	}
}