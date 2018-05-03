package ch.ti8m.eventregistration.backend.controller

import ch.ti8m.eventregistration.backend.converter.convert
import ch.ti8m.eventregistration.backend.entity.EventEntity
import ch.ti8m.eventregistration.backend.repository.EventRepository
import ch.ti8m.eventregistration.common.model.Event
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class EventController(@Autowired val eventRepository: EventRepository) {

    @CrossOrigin
    @PostMapping("/events")
    fun addEvent(@RequestBody event: Event): Event = convert(eventRepository.save(convert(event)))

    @CrossOrigin
    @GetMapping("/events")
    fun findAll(): List<Event> = convert(eventRepository.findAll())


}