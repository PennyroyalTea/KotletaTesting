package com.kotletaTesting

import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty

fun main(args: Array<String>): Unit {
    val server = embeddedServer(Netty, 8000) {
        routing {
            get("/") {
                call.respondText("Main Page")
            }
            get("/new") {
                 call.respondText { "Create new problem" }
            }
            get("/problem/{id}") {
                val id = call.parameters["id"]
                call.respondText("Problem id = $id")
            }
        }
    }

    server.start(wait=true)
}