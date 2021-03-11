package com.spyrdonapps.weightreductor.backend.routing

import com.spyrdonapps.weightreductor.backend.deletelater.Weighing
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.datetime.Instant

fun Route.weighings(repository: WeighingsRepository) {

    location<WeighingLocation> {
        get {
            call.respond(repository.getAllWeighings())
        }
        get("/{date}") {
            val dateParam = call.parameters["date"] ?: run {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "No date received"
                )
                return@get
            }
            val date: Instant
            try {
                date = Instant.parse(dateParam)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Wrong date format"
                )
                return@get
            }
            call.respond(repository.getByDate(date))
        }
        post {
            val weighing = call.receive<Weighing>()
            repository.upsert(weighing)
            call.respond(HttpStatusCode.OK)
        }
        delete("/{date}") {
            val dateParam = call.parameters["date"] ?: run {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "No date received"
                )
                return@delete
            }
            val date: Instant
            try {
                date = Instant.parse(dateParam)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Wrong date format"
                )
                return@delete
            }
            repository.deleteByDate(date)
            call.respond(HttpStatusCode.OK)
        }
    }
}