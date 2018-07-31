package dmax.ktor.demo.modules

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.body
import kotlinx.html.p

fun Application.help() = routing {

    get("/") {
        call.respondHtml {
            body {
                p { +"Endpoints:" }
                p { +"/connect" }
                p { +"/status" }
            }
        }
    }

    get("/status") {
        call.respondText { "Status: ok" }
    }
}
