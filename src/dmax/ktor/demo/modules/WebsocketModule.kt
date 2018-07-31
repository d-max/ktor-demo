package dmax.ktor.demo.modules

import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.websocket.webSocket
import kotlinx.coroutines.experimental.channels.consumeEach

data class ChatSession(val int: Int)

fun Application.websockets() = routing {

    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }

    intercept(ApplicationCallPipeline.Infrastructure) {
        if (call.sessions.get<ChatSession>() == null) {
            call.sessions.set(ChatSession(1))
        }
    }

    webSocket("/connect") {

        log.info("ws opened")
        log.info(Thread.currentThread().id.toString())

        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }

        incoming.consumeEach {
            log.info("consume foreach")
            if (it is Frame.Text) {
                log.info("text received")
                log.info(it.readText())
            }
        }

        log.info("end")
    }
}
