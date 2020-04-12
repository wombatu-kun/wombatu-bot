package wombatukun.bots.wombatubot.services

import org.springframework.boot.test.context.SpringBootTest
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.bots.wombatubot.MockingUtils

@SpringBootTest
class UserServiceTest: MockingUtils() {

	//@Autowired lateinit var userService: UserService

	//@Test
	fun testDispatchStart() {
		val update: Update = buildMockUpdate("/start", ChatType.private)

	}

}