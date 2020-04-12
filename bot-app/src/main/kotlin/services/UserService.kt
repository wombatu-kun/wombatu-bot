package wombatukun.bots.wombatubot.services

import org.telegram.telegrambots.meta.api.objects.User

interface UserService {

	fun upsertUser(from: User)

}