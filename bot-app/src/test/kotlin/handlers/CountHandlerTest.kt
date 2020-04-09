package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import wombatukun.bots.wombatubot.MockingUtils

class CountHandlerTest: MockingUtils() {

	private val countHandler = CountHandler();

	@Test
	fun testMan() {
		assertEquals("Количество символов: каунт абырвалг", countHandler.man())
	}

	@Test
	fun testSuperGroupMessageNotMatch() {
		assertFalse(countHandler.matches(buildMockUpdate("text", ChatType.supergroup)))
	}

	@Test
	fun testMessageTextNotMatch() {
		assertFalse(countHandler.matches(buildMockUpdate("каунт", ChatType.private)))
	}

	@Test
	fun testMessageTextMatch() {
		assertTrue(countHandler.matches(buildMockUpdate("каунт абырвалг", ChatType.private)))
	}

	@Test
	fun testHandle() {
		val response: SendMessage = countHandler.handle(buildMockUpdate("каунт абырвалг", ChatType.private))
		assertEquals("8", response.text)
		assertEquals(CHAT_ID.toString(), response.chatId)
	}
}