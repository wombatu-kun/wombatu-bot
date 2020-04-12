package wombatukun.bots.wombatubot.dao.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import wombatukun.bots.wombatubot.dao.entities.User
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTests (
		@Autowired private val userRepository: UserRepository
) {

	@Test
	fun testRepository() {
		val user1 = User(id = 1L, userName = "user1", firstName = "u1")
		val user2 = User(id = 2L, userName = "user2", firstName = "u2")
		userRepository.saveAll(listOf(user1, user2))

		var all = userRepository.findAll()
		assertEquals(2, all.size)

		var found = userRepository.findById(user1.id!!).get()
		assertThat(found).isEqualTo(user1)
		println(found)
		found.lastTime = LocalDateTime.now()
		found.lang = "en"
		userRepository.save(found)

		found = userRepository.findById(user2.id!!).get()
		assertThat(found).isEqualTo(user2)
		println(found)

		userRepository.delete(user2)

		all = userRepository.findAll()
		assertEquals(1, all.size)
		assertThat(all[0]).isEqualTo(user1)
		assertEquals("en", all[0].lang)
		println(all[0])
	}
}