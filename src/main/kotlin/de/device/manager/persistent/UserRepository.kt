package de.device.manager.persistent

import de.device.manager.persistent.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    override fun findById(id: UUID): Optional<User>
}