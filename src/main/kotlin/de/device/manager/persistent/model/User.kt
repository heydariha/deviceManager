package de.device.manager.persistent.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        val id: UUID,
        @NotEmpty
        val firstName: String,
        @NotEmpty
        val lastName: String,
        val address: String,
        val birthday: LocalDate,
        @ManyToOne
        var device: Device? = null
){
        constructor() : this(UUID.randomUUID(), "", "", "", LocalDate.now(), null)
}