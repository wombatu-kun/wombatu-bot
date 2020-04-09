package wombatukun.bots.wombatubot.services

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

interface MessageDispatcher {

	fun dispatch(update: Update): SendMessage?

}