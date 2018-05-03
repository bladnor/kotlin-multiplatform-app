package ch.ti8m.eventregistration.backend.entity

import javax.persistence.*

@Entity
@Table(name = "event")
data class EventEntity(
        val name: String = "",
        val description: String = "",
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = 0
)