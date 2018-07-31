@file:JvmName("Main")
package dmax.ktor.demo

import dmax.ktor.demo.modules.features
import dmax.ktor.demo.modules.help
import dmax.ktor.demo.modules.websockets
import io.ktor.server.cio.CIO
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer

fun main(args: Array<String>) {
    val environment = applicationEngineEnvironment {
        module {
            features()
            help()
            websockets()
        }
        connector {
            port = 8080
        }
    }
    embeddedServer(CIO, environment).start(wait = true)
}
