package wombatukun.bots.wombatubot.dao.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wombatukun.bots.wombatubot.dao.entities.User

@Repository
interface UserRepository: JpaRepository<User, Long> {
}