package wombatukun.bots.wombatubot.services

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import wombatukun.bots.wombatubot.MockingUtils

@SpringBootTest
class UserServiceTest: MockingUtils() {

	@Autowired
	private lateinit var userService: UserService

	@Test
	fun testFindByIdNotFoundException() {
		assertThrows<NoSuchElementException> { userService.findById(0L) }
	}

	@Test
	@Sql(scripts = ["/sql/clean.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	fun testUpsertUser() {
		assertEquals(0, userService.listUsers().size)
		runBlocking { userService.upsertUser(USER) }
		var users = userService.listUsers()
		assertEquals(1, users.size)
		var user = users[0]
		assertEquals(USER.id.toLong(), user.id)
		assertEquals(USER.userName, user.userName)
		assertEquals(USER.firstName, user.firstName)
		assertEquals(USER.lastName, user.lastName)
		assertNotNull(user.firstTime)
		assertNotNull(user.lastTime)
		assertEquals(USER.bot, user.bot)
		assertEquals(USER.languageCode, user.lang)

		val firstTime = user.firstTime
		val lastTime = user.lastTime

		val updUser = org.telegram.telegrambots.meta.api.objects.User(1, "Васёк", false, "Пупкен", null, "ru")
		runBlocking { userService.upsertUser(updUser) }
		users = userService.listUsers()
		assertEquals(1, users.size)
		user = users[0]
		assertEquals(updUser.id.toLong(), user.id)
		assertEquals(updUser.userName, user.userName)
		assertEquals(updUser.firstName, user.firstName)
		assertEquals(updUser.lastName, user.lastName)
		assertEquals(firstTime, user.firstTime)
		assertNotEquals(lastTime, user.lastTime)
		assertEquals(updUser.bot, user.bot)
		assertEquals(updUser.languageCode, user.lang)
	}
}