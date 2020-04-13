package wombatukun.bots.wombatubot.services

import wombatukun.bots.wombatubot.dao.entities.User


interface UserService {

	fun upsertUser(from: org.telegram.telegrambots.meta.api.objects.User)
	fun listUsers(): List<User>

}