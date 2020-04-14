package wombatukun.bots.wombatubot.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import wombatukun.bots.wombatubot.MockingUtils

class FixKeyboardHandlerTest: MockingUtils() {

	private val fixKeyboardHandler = FixKeyboardHandler();

	private val textIn = ".,юЮбБьЬтТиИмМсСчЧяЯжЖдДлЛоОрРпПаАвВыЫфФъЪхХзЗщЩшШгГнНеЕкКуУцЦйЙёЁэЭ\"№;:?"
	private val textOu = "/?.>,<mMnNbBvVcCxXzZ;:lLkKjJhHgGfFdDsSaA]}[{pPoOiIuUyYtTrReEwWqQ`~'\"@#$^&"

	@Test
	fun testMan() {
		assertEquals("Фикс в rus-раскладку: рус ghbdtn\nФикс в eng-раскладку: енг руддщ", fixKeyboardHandler.man())
	}

	@Test
	fun testGroupMessageNotMatch() {
		assertFalse(fixKeyboardHandler.matches(buildMockUpdate("рус лалала", ChatType.group)))
	}

	@Test
	fun testMessageTextNotMatch() {
		assertFalse(fixKeyboardHandler.matches(buildMockUpdate("шмус", ChatType.private)))
	}

	@Test
	fun testRusMatch() {
		assertTrue(fixKeyboardHandler.matches(buildMockUpdate("рус asdf", ChatType.private)))
	}

	@Test
	fun testEngMatch() {
		assertTrue(fixKeyboardHandler.matches(buildMockUpdate("енг йцук", ChatType.private)))
	}

	@Test
	fun testEngHandle() {
		val responses = fixKeyboardHandler.handle(buildMockUpdate("енг $textIn", ChatType.private))
		assertEquals(1, responses.size)
		assertEquals(textOu, responses[0].text)
		assertEquals(CHAT_ID.toString(), responses[0].chatId)
	}

	@Test
	fun testRusHandle() {
		val responses = fixKeyboardHandler.handle(buildMockUpdate("рус $textOu", ChatType.private))
		assertEquals(1, responses.size)
		assertEquals(textIn, responses[0].text)
		assertEquals(CHAT_ID.toString(), responses[0].chatId)
	}
}