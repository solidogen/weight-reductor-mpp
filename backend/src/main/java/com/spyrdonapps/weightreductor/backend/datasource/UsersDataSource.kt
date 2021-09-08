@file:Suppress("UnusedImport")

package com.spyrdonapps.weightreductor.backend.datasource

import at.favre.lib.crypto.bcrypt.BCrypt
import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.common.model.*
import com.spyrdonapps.weightreductor.backend.database.tables.UsersTable
import com.spyrdonapps.weightreductor.backend.util.utils.PasswordHasher
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface UsersDataSource {
    suspend fun checkCredentials(userCredentials: UserCredentials): Boolean
    suspend fun register(userCredentials: UserCredentials)
}

class ExposedUsersDataSource(private val passwordHasher: PasswordHasher) : UsersDataSource {

    override suspend fun checkCredentials(userCredentials: UserCredentials): Boolean =
        newSuspendedTransaction {
            val storedPasswordHash =
                UsersTable.select { UsersTable.username eq userCredentials.username }
                    .map { it[UsersTable.passwordHash] }.first()
            passwordHasher.verifyHashes(userCredentials.password, storedPasswordHash)
        }

    override suspend fun register(userCredentials: UserCredentials) {
        newSuspendedTransaction {
            val passwordHash = passwordHasher.hashPassword(userCredentials.password)
            UsersTable.insert {
                it[username] = userCredentials.username
                it[this.passwordHash] = passwordHash
            }
        }
    }
}