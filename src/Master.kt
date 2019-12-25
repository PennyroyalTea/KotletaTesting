package com.kotletaTesting

import io.ktor.application.call
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty

import io.ktor.request.receiveParameters
import kotlinx.html.*

fun main(args: Array<String>): Unit {
    val server = embeddedServer(Netty, 8000) {
        routing {
            get("/") {
                call.respondHtml {
                    head {
                        title {
                            + "kotleta"
                        }
                    }
                    body {
                        h2 {
                            + "Problems:"
                        }
                        Storage.problems.forEach {
                            ul {
                                li {
                                    a (href = "/problem/${it.value.id}"){
                                        + it.value.title
                                    }
                                }
                            }
                        }
                        a (href = "/new") {
                            + "add new!"
                        }
                    }
                }
            }
            get("/new") {
                call.respondHtml {
                    head {
                        title {
                            + "new problem"
                        }
                    }
                    body {
                        form {
                            method = FormMethod.post
                            action = "/new"
                            + "Problem name:"
                            br
                            input (type = InputType.text) {
                                name = "title"
                            }
                            br
                            + "Problem description:"
                            br
                            textArea {
                                name = "description"
                            }
                            br
                            input (type = InputType.submit) {
                                value = "submit"
                            }
                        }
                    }
                }
            }
            post("/new") {
                val params = call.receiveParameters()
                Storage.addProblem(params["title"] ?: "", params["description"] ?: "")
                call.respondText { "the problem ${params["title"]} was created!" }
            }
            get("/problem/{id}") {
                val id : Int = call.parameters["id"]?.toInt() ?: -1
                val problem = Storage.problems[id]
                call.respondHtml {
                    head {
                        title {
                            + ("$problem")
                        }
                    }
                    body {
                        h1 {
                            +("${problem?.title}")
                        }
                        div {
                            +("${problem?.description}")
                        }
                    }
                }
            }
        }
    }

    server.start(wait=true)
}