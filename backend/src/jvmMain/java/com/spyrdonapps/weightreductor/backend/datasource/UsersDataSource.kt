@file:Suppress("UnusedImport")

package com.spyrdonapps.weightreductor.backend.datasource

import at.favre.lib.crypto.bcrypt.BCrypt
import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.common.model.*
import com.spyrdonapps.weightreductor.backend.database.tables.UsersTable
import com.spyrdonapps.weightreductor.backend.util.utils.PasswordHasher
import com.spyrdonapps.weightreductor.backend.util.utils.UserId
import com.spyrdonapps.weightreductor.backend.util.utils.httpStatusException
import io.ktor.http.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface UsersDataSource {
    suspend fun checkCredentials(userCredentials: UserCredentials): UserId
    suspend fun register(userCredentials: UserCredentials): UserId
}

class ExposedUsersDataSource(private val passwordHasher: PasswordHasher) : UsersDataSource {

    override suspend fun checkCredentials(userCredentials: UserCredentials): UserId =
        newSuspendedTransaction {
            val storedUser =
                UsersTable.select { UsersTable.username eq userCredentials.username }.firstOrNull()
                    ?: httpStatusException(HttpStatusCode.Conflict, "Wrong credentials")
            val storedPasswordHash = storedUser[UsersTable.passwordHash]
            val isUserVerified =
                passwordHasher.verifyHashes(userCredentials.password, storedPasswordHash)
            if (isUserVerified) {
                storedUser[UsersTable.id].value
            } else {
                httpStatusException(HttpStatusCode.Conflict, "Wrong credentials")
            }
        }

    override suspend fun register(userCredentials: UserCredentials): UserId =
        newSuspendedTransaction {
            val passwordHash = passwordHasher.hashPassword(userCredentials.password)
            UsersTable.insertAndGetId {
                it[username] = userCredentials.username
                it[this.passwordHash] = passwordHash
            }.value
        }
}