package com.utsman

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(HSTS) {
        includeSubDomains = true
    }

    routing {
        get("/") {
            call.respondText("notatod", contentType = ContentType.Text.Plain)
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/version") {
            val macOs = mapOf(
                "version_code" to 1,
                "version_name" to "1.0.0-alpha",
                "changelog" to listOf(
                    "Fix enable Google Auth",
                    "Fix local data"
                )
            )
            call.respond(
                mapOf(
                    "macOS" to macOs
                )
            )
        }

        get("/feature") {
            call.respond(
                mapOf(
                    "google_auth" to false
                )
            )
        }
    }
}

