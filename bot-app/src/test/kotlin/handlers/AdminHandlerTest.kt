package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import wombatukun.bots.wombatubot.MockingUtils
import wombatukun.bots.wombatubot.dao.entities.User
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
	fun testListMatch() {
		assertTrue(adminHandler.matches(buildMockUpdate("лист", ChatType.private)))
	}

	@Test
	fun testMsgMatch() {
		assertTrue(adminHandler.matches(buildMockUpdate("мсг 1 ололо", ChatType.private)))
	}

	@Test
	fun testListHandle() {
		val response: SendMessage = adminHandler.handle(buildMockUpdate("лист", ChatType.private))
		assertEquals("", response.text)
		assertEquals(CHAT_ID.toString(), response.chatId)
	}

	@Test
	fun testMsgHandleSuccess() {
		`when`(userService.findById(1)).thenReturn(User(1))
		val response: SendMessage = adminHandler.handle(buildMockUpdate("мсг 1 ололо", ChatType.private))
		assertEquals("ололо", response.text)
		assertEquals("1", response.chatId)
	}
}