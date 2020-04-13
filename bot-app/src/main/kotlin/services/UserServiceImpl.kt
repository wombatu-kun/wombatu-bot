package wombatukun.bots.wombatubot.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wombatukun.bots.wombatubot.dao.entities.User
import wombatukun.bots.wombatubot.dao.repositories.UserRepository
import java.time.LocalDateTime

@Service
class UserServiceImpl(
		@Autowired private val userRepository: UserRepository
): UserService {

	@Transactional
	override suspend fun upsertUser(from: org.telegram.telegrambots.meta.api.objects.User) {
		val id = from.id.toLong()
		val user = userRepository.findById(id).orElse(User(id))
		user.userName = from.userName
		user.firstName = from.firstName
		user.lastName = from.lastName
		user.bot = from.bot
		user.lang = from.languageCode
		user.lastTime = LocalDateTime.now()
		userRepository.save(user)
	}

	@Transactional(readOnly = true)
	override fun listUsers(): List<User> {
		return userRepository.findAll()
	}

	@Transactional(readOnly = true)
	override fun findById(id: Long): User {
		return userRepository.findById(id).get()
	}
}