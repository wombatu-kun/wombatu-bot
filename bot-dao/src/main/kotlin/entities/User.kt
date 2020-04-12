package wombatukun.bots.wombatubot.dao.entities

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name= "users")
data class User (

		@Id
		var id: Long?,

		@Column(name = "first_time")
		var firstTime: LocalDateTime = LocalDateTime.now(),
		@Column(name = "last_time")
		var lastTime: LocalDateTime = LocalDateTime.now(),

		var userName: String = "",
		var firstName: String? = null,
		var lastName: String? = null,

		@Column(name = "is_bot")
		var bot: Boolean = false,
		var lang: String? = null
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) {
			return true
		}
		if (other == null || javaClass != other.javaClass) {
			return false
		}
		val user: User = other as User
		return id == user.id
	}

	override fun hashCode(): Int {
		return Objects.hashCode(id)
	}
}