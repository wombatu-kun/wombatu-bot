package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import wombatukun.bots.wombatubot.MockingUtils
import wombatukun.bots.wombatubot.services.UserService

class AdminHandlerTest: MockingUtils() {

	private val userService = Mockito.mock(UserService::class.java)
	private val adminHandler = AdminHandler("1", userService)

	@Test
	fun testMan() {
		assertEquals("", adminHandler.man())
	}

	@Test
	fun testChannelMessageNotMatch() {
		assertFalse(adminHandler.matches(buildMockUpdate("text", ChatType.channel)))
	}

	@Test
	fun testMessageTextNotMatch() {
		assertFalse(adminHandler.matches(buildMockUpdate("реверс", ChatType.private)))
	}

	@Test
	fun testMessageTextMatch() {
		assertTrue(adminHandler.matches(buildMockUpdate("лист", ChatType.private)))
	}

	@Test
	fun testHandle() {
		val response: SendMessage = adminHandler.handle(buildMockUpdate("лист", ChatType.private))
		assertEquals("", response.text)
		assertEquals(CHAT_ID.toString(), response.chatId)
	}
}