package ch.ti8m.eventregistration.android

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import ch.ti8m.eventregistration.android.EventRegistrationApplication.Companion.logTag
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var requestQueue: RequestQueue? = null
    val url = "http://167.99.132.183:8080/events"
    val listType = object : TypeToken<List<Event>>() {}.type
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        eventSaveButton.setOnClickListener { button -> saveEvents(eventNameText.text, eventDescriptionText.text) }
        eventFetchButton.setOnClickListener({ fetchEvents() })


        this.requestQueue = Volley.newRequestQueue(this)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun saveEvents(eventName: Editable?, description: Editable?) {
        val jsonString = gson.toJson(Event(eventName.toString(), description.toString()))

        val getEventsRequest = PostEventRequest(
                Request.Method.POST,
                url,
                JSONObject(jsonString),
                Response.Listener { response ->
                    Log.i(logTag, "request successfull $response")
                    fetchEvents()
                },
                Response.ErrorListener { error -> Log.e(logTag, "request failure  $error") })

        requestQueue?.add(getEventsRequest)

    }

    private fun fetchEvents() {

        val getEventsRequest = JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener { response ->
                    Log.i(logTag, "request successfull ${response.toString()}")
                    val eventList = gson.fromJson<List<Event>>(response.toString(), listType)
                    val eventTextLines = eventList.map { "${it.name} / ${it.description}" }.joinToString("\n")
                    eventListText.setText(eventTextLines)
                },
                Response.ErrorListener { error -> Log.e(logTag, "request failure  $error") })

        requestQueue?.add(getEventsRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

data class Event(val name: String, val description: String)

class PostEventRequest(
        method: Int,
        url: String,
        body: JSONObject,
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener)
    : JsonObjectRequest(method, url, body, listener, errorListener) {

    override fun getHeaders(): MutableMap<String, String> {
        return mutableMapOf(Pair("Content-Type", "application/json"))
    }
}


