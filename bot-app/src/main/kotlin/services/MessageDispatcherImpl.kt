package wombatukun.bots.wombatubot.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.api.currency.CurrencyApi
import wombatukun.bots.wombatubot.handlers.*

@Service
class MessageDispatcherImpl(
		@Autowired private val userService: UserService,
		@Autowired private val currencyApi: CurrencyApi
): MessageDispatcher {

	private val handlers: List<MessageHandler> = listOf(
			CurrencyHandler(currencyApi),
			FixKeyboardHandler(),
			CapslockHandler(),
			CountHandler(),
			ReverseHandler(),
			DummyHandler()
	)

	override fun dispatch(update: Update): SendMessage? {
		val message: Message? = update.message
		if (message?.isUserMessage == true) {
			userService.upsertUser(update.message.from)
			if (message.text == "ман" || message.text == "/start") {
				return MessageHandler.buildSimpleResponse(message.chatId,
						handlers.map { handler -> handler.man() }.joinToString("\n"))
			}
		}
		return handlers.find { handler -> handler.matches(update) }
				?.handle(update)
	}
}