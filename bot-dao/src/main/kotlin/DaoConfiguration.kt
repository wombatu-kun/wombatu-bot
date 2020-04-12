package wombatukun.bots.wombatubot.dao

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories("wombatukun.bots.wombatubot.dao.repositories")
@EntityScan("wombatukun.bots.wombatubot.dao.entities")
@EnableTransactionManagement
class DaoConfiguration {

}