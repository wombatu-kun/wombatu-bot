package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import wombatukun.bots.wombatubot.MockingUtils

class TransmitterHandlerTest: MockingUtils() {

	private val transmitterHandlerOn = TransmitterHandler("2")
	private val transmitterHandlerOff = TransmitterHandler("2", false)

	@Test
	fun testMan() {
		assertEquals("", transmitterHandlerOn.man())
	}

	@Test
	fun testGroupMessageNotMatches() {
		assertFalse(transmitterHandlerOn.matches(buildMockUpdate("text", ChatType.group)))
	}

	@Test
	fun testMessageTextMatches() {
		assertTrue(transmitterHandlerOn.matches(buildMockUpdate("абырвалг", ChatType.private)))
	}

	@Test
	fun testTransmitterOffNotMatches() {
		assertFalse(transmitterHandlerOff.matches(buildMockUpdate("абырвалг", ChatType.private)))
	}

	@Test
	fun testHandle() {
		val responses = transmitterHandlerOn.handle(buildMockUpdate("абырвалг", ChatType.private))
		assertEquals(1, responses.size)
		assertEquals("1 (@username): абырвалг", responses[0].text)
		assertEquals("2", responses[0].chatId)
	}
}