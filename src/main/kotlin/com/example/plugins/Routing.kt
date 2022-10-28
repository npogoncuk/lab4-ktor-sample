package com.example.plugins

import com.example.builder.CurrentWeatherBuilder
import com.example.builder.Director
import com.example.dao.DAOFacade
import com.example.dao.DAOFacadeImpl
import com.example.models.Customer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val customerStorage = mutableListOf<Customer>()
    val weatherStorage = mutableListOf<String>()

    routing {
        route("/customer") {
            get {
                if (customerStorage.isNotEmpty()) {
                    call.respond(customerStorage)
                } else {
                    call.respondText("No customers found", status = HttpStatusCode.OK)
                }
            }
            get("{id?}") {
                val id = call.parameters["id"] ?: return@get call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest
                )
                val customer =
                    customerStorage.find { it.id == id } ?: return@get call.respondText(
                        "No customer with id $id",
                        status = HttpStatusCode.NotFound
                    )
                call.respond(customer)
            }
            post {
                val customer = call.receive<Customer>()
                customerStorage.add(customer)
                call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
            }
            delete("{id?}") {
                val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                if (customerStorage.removeIf { it.id == id }) {
                    call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
                } else {
                    call.respondText("Not Found", status = HttpStatusCode.NotFound)
                }
            }
        }

        val director = Director(CurrentWeatherBuilder())

        route("/weather") {
            val dao: DAOFacade = DAOFacadeImpl()
            get("/current_weather") {
                val client = HttpClient(CIO) {
                    expectSuccess = true
                }
                val response: String = client.get(director.makeCurrentWeatherUrl()).body()
//                Json.decodeFromJsonElement(WeatherResponse.serializer(), Json.parseToJsonElement(response))
//                call.respondText(response, status = HttpStatusCode.OK)
                call.respond(response)
                client.close()
            }
            get("/save_weather") {
                val client = HttpClient(CIO) {
                    expectSuccess = true
                }
                val response: String = client.get(director.makeCurrentWeatherUrl()).body()
                //weatherStorage += response
                dao.addNewWeatherResponse(response)
                call.respond(response)
                client.close()
            }
            get("/saved") {
                val saved = dao.allWeatherResponses()
                if (saved.isNotEmpty()) call.respond(saved)
                else call.respondText("No customers found", status = HttpStatusCode.OK)
            }

        }

    }

}
