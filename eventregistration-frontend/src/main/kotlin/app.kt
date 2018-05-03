import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.fetch.*
import kotlin.browser.document
import kotlin.browser.window

data class Event(val name: String = "", val description: String = "", val id: Long = 0)

var alertDiv: HTMLDivElement? = null
var eventListContainer: HTMLTextAreaElement? = null

val DESCRIPTION = "description"
val EVENTNAME = "eventname"
val EVENTLISTCONTAINER = "eventListContainer"
val ALERT = "alert"


fun main(args: Array<String>) {

    val appContentContainer = document.getElementById("container")

    appContentContainer?.append(appContent)
    val div = document.create.div {}
    appContentContainer?.append(div)
    alertDiv = document.getElementById("${ALERT}Id") as HTMLDivElement
    eventListContainer = document.getElementById("${EVENTLISTCONTAINER}Id") as HTMLTextAreaElement
    window.onload = {
        fetchEvents()
    }

}

/**
 * Create Layout
 */
val appContent = document.create.div {
    div {
        id = "${ALERT}Id"
    }

    h1 {
        +"Event Registration"
    }

    div(classes = "form-group") {
        label {
            +EVENTNAME.capitalize()
            htmlFor = EVENTNAME
        }
        textInput(classes = "form-control") {
            placeholder = EVENTNAME.capitalize()
            name = EVENTNAME
            id = "${EVENTNAME}Id"
        }
    }
    div(classes = "form-group") {
        label {
            +DESCRIPTION.capitalize()
            htmlFor = DESCRIPTION
        }
        textInput(classes = "form-control") {
            placeholder = DESCRIPTION.capitalize()
            name = DESCRIPTION
            id = "${DESCRIPTION}Id"
        }
    }

    button(classes = "btn btn-default", type = ButtonType.button) {
        onClickFunction = (fun(event) {
            val name = (document.getElementById("${EVENTNAME}Id") as HTMLInputElement).value
            val description = (document.getElementById("${DESCRIPTION}Id") as HTMLInputElement).value
            val evente = Event(name, description)
            addEvent(evente)
        })

        +"submit"
    }

    div(classes = "card") {
        style = "margin-top:20px"
        div(classes = "card-header") {
            +"Event Liste"
        }
        textArea {
            id = "${EVENTLISTCONTAINER}Id"
        }
    }
}

/**
 * GET Events from backend
 */
fun fetchEvents() {

    window
            .fetch(Request("http://localhost:8080/events"))
            .then { response ->
                response.text()
                        .then { body ->
                            val objArray = JSON.parse<Array<Event>>(body)
                            eventListContainer?.value = ""
                            objArray.forEach {
                                eventListContainer?.value += "${it.name}  /  ${it.description}\n"
                            }
                        }
            }
            .catch { println(it) }
}

/**
 * POST Events to backend
 */
fun addEvent(event: Event) {
    val headers = Headers()
    headers.append("Content-Type", "application/json")
    val body = JSON.stringify(event)

    window
            .fetch("http://localhost:8080/events", RequestInit(method = "POST", headers = headers, body = body, referrerPolicy = "origin", cache = RequestCache.NO_CACHE, redirect = RequestRedirect.MANUAL))
            .then {
                when (it.status) {
                    200.toShort() -> {
                        setAlertMessage("Event gespeichert", "alert alert-success")
                    }
                    else -> {
                        setAlertMessage("Fehler beim speichern. Http Status '${it.status}'")
                    }
                }
            }
            .catch {
                setAlertMessage("Exception beim speichern '${it.message}'")
            }
            .then { fetchEvents() } // Refetch Events after POST
}

private fun setAlertMessage(message: String, cssClass: String = "alert alert-warning") {
    alertDiv?.textContent = message
    alertDiv?.className = cssClass
}





