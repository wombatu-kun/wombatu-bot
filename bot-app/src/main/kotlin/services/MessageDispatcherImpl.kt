package wombatukun.bots.wombatubot.services

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.api.currency.CurrencyApi
import wombatukun.bots.wombatubot.config.BotConfig
import wombatukun.bots.wombatubot.handlers.*


@Service
class MessageDispatcherImpl(
		@Autowired private val botConfig: BotConfig,
		@Autowired private val userService: UserService,
		@Autowired private val currencyApi: CurrencyApi
): MessageDispatcher {

	private val handlers: List<MessageHandler> = listOf(
			CurrencyHandler(currencyApi),
			FixKeyboardHandler(),
			CapslockHandler(),
			CountHandler(),
			AdminHandler(botConfig.botAdmin, userService),
			TransmitterHandler(botConfig.botAdmin, botConfig.transmitterOn.toBoolean())
	)
	private val startHandler = StartHandler(handlers)

	override fun dispatch(update: Update): List<SendMessage> {
		if (update.message?.isUserMessage == true) {
			GlobalScope.launch { userService.upsertUser(update.message.from) }
			if (startHandler.matches(update)) {
				return startHandler.handle(update)
			}
		}
		return handlers.filter { it.matches(update) }.map { it.handle(update) }.flatten()
	}
}