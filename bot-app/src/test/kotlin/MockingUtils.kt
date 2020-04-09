package wombatukun.bots.wombatubot

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User


open class MockingUtils {

	enum class ChatType { private, group, channel, supergroup }

	val USER: User = User(1, "first", false, "last", "username", null)
	val CHAT_ID = 23L

	fun buildMockUpdate(text: String?, chatType: ChatType): Update {
		val message = mock(Message::class.java)
		`when`(message.from).thenReturn(USER)
		`when`(message.text).thenReturn(text)
		`when`(message.hasText()).thenReturn(true)
		`when`(message.chatId).thenReturn(CHAT_ID)

		when (chatType) {
			ChatType.private -> `when`(message.isUserMessage).thenReturn(true)
			ChatType.group -> `when`(message.isGroupMessage).thenReturn(true)
			ChatType.channel -> `when`(message.isChannelMessage).thenReturn(true)
			ChatType.supergroup -> `when`(message.isSuperGroupMessage).thenReturn(true)
		}

		val update = mock(Update::class.java)
		`when`(update.hasMessage()).thenReturn(true)
		`when`(update.message).thenReturn(message)
		return update
	}
}