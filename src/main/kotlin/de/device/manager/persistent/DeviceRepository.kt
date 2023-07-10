package de.device.manager.persistent

import de.device.manager.persistent.model.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeviceRepository : JpaRepository<Device, UUID> {
    override fun findById(id: UUID): Optional<Device>
}