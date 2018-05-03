package ch.ti8m.eventregistration.backend.controller

import ch.ti8m.eventregistration.backend.entity.EventEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class EventControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate


    @Test
    fun `assert that stored event machtes the description`() {
        val description = "Test Segelturn"
        Assertions.assertThat(this.restTemplate.postForObject("/events", EventEntity(description = description), EventEntity::class.java).description).isEqualTo(description)
    }

    @Test
    fun `assert that stored event id is greater than zero`() {
        val description = "Test Segelturn"
        Assertions.assertThat(this.restTemplate.postForObject("/events", EventEntity(description = description), EventEntity::class.java).id).isGreaterThan(0)
    }

}