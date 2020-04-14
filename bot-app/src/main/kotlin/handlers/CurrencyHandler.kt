package wombatukun.bots.wombatubot.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import wombatukun.api.currency.CurrencyApi
import wombatukun.api.currency.CurrencyRequest
import wombatukun.api.currency.CurrencyResponse
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.buildSimpleResponse
import wombatukun.bots.wombatubot.handlers.MessageHandler.Companion.formatter
import java.time.LocalDate

class CurrencyHandler(
		private val currencyApi: CurrencyApi
): MessageHandler {

	override fun man(): String {
		return """
			Текущий курс: руб
			Курс на дату: руб ДД.ММ.ГГГГ
		""".trimIndent()
	}

	override fun matches(update: Update): Boolean {
		val msg: String? = update.message?.text
		return update.message.isUserMessage && msg != null && (msg == "руб" || msg.startsWith("руб "))
	}

	override fun handle(update: Update): List<SendMessage> {
		val msg: String = update.message.text
		val text = when {
			msg == "руб" -> buildCurrencyText(currencyApi.getExchangeRates(CurrencyRequest()))
			msg.matches(Regex("^руб \\d{2}\\.\\d{2}\\.\\d{4}")) -> try {
				val date: LocalDate = LocalDate.parse(msg.substringAfter(' '), formatter)
				buildCurrencyText(currencyApi.getExchangeRates(CurrencyRequest(date)))
			} catch (e: Exception) {
				man()
			}
			else -> man()
		}
		return listOf(buildSimpleResponse(update.message.chatId, text))
	}

	private fun buildCurrencyText(response: CurrencyResponse): String {
		if (response.rates.isNullOrEmpty()) {
			return "${response.date.format(formatter)}: не удалось получить курсы валют"
		} else {
			val usd = response.rates[0].rate;
			val eur = response.rates[1].rate;
			return "${response.date.format(formatter)}: $ = ${usd}₽, € = ${eur}₽"
		}
	}
}