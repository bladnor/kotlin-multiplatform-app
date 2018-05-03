package ch.ti8m.eventregistration.backend.repository

import ch.ti8m.eventregistration.backend.entity.EventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<EventEntity, Long>