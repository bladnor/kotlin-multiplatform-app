package ch.ti8m.eventregistration.backend.converter

import ch.ti8m.eventregistration.backend.entity.EventEntity
import ch.ti8m.eventregistration.common.model.Event

fun convert(eventEntity: EventEntity): Event {
    val (name, description, id) = eventEntity

    return Event(name, description, id)
}

fun convert(event: Event): EventEntity {
    val (name, description, id) = event

    return EventEntity(name, description, id)
}

fun convert(eventEntities: List<EventEntity>): List<Event> {

    return eventEntities
            .map {
                val (name, description, id) = it
                Event(name, description, id)
            }
            .toList()

}
