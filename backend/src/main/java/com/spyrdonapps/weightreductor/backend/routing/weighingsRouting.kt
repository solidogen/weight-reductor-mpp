@file:Suppress("UnusedImport")

package com.spyrdonapps.weightreductor.backend.routing

import com.spyrdonapps.common.model.*
import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.datetime.Instant

fun Route.weighings(repository: WeighingsRepository) {
    get(ApiEndpoints.weighings) {
        call.respond(repository.getAllWeighings())
    }
    get("${ApiEndpoints.weighings}/{id}") {
        val id = call.parameters["id"] ?: error("No id provided")
        call.respond(repository.getById(id.toLong()))
    }
    authenticate {
        post(ApiEndpoints.weighingsAdd) {
            val weighing = call.receive<Weighing>()
            repository.upsert(weighing)
            call.respond(HttpStatusCode.OK)
        }
    }
    delete("${ApiEndpoints.weighings}/{date}") {
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
