package dmax.ktor.demo.modules

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.websocket.WebSockets
import org.slf4j.event.Level
import java.time.Duration

fun Application.features() {
    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.INFO
    }
    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter())
    }
    install(WebSockets) {
        timeout = Duration.ofSeconds(15)
        pingPeriod = Duration.ofSeconds(30)
    }
}
