package de.device.manager.persistent.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*


@Entity
data class Device(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val uuid: UUID,
        val serialNumber: String,
        val phoneNumber: String,
        val model: String,
){
        constructor() : this(UUID.randomUUID() , "", "", "")
}