package wombatukun.bots.wombatubot.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.MockingUtils
import java.util.concurrent.TimeUnit

@SpringBootTest
class MessageDispatcherTest: MockingUtils() {

	@Autowired
	private lateinit var userService: UserService

	@Autowired
	private lateinit var messageDispatcher: MessageDispatcher

	private val MAN: String = """
		Текущий курс: руб
		Курс на дату: руб ДД.ММ.ГГГГ
		Фикс в rus-раскладку: рус ghbdtn
		Фикс в eng-раскладку: енг руддщ
		Анкапс: анкапс абЫрВАЛГ
		Количество символов: каунт абырвалг
		""".trimIndent()

	@Test
	@Sql(scripts = ["/sql/clean.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	fun testDispatchStart() {
		assertEquals(0, userService.listUsers().size)
		val update: Update = buildMockUpdate("/start", ChatType.private)
		val responses = messageDispatcher.dispatch(update)
		assertEquals(1, responses.size)
		assertEquals(MAN, responses[0].text)
		assertEquals(CHAT_ID.toString(), responses[0].chatId)

		TimeUnit.MILLISECONDS.sleep(200);

		val users = userService.listUsers()
		assertEquals(1, users.size)
	}
}