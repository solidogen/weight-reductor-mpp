package com.spyrdonapps.weightreductor.backend.util.utils

import at.favre.lib.crypto.bcrypt.BCrypt
import co.touchlab.kermit.Kermit

interface PasswordHasher {
    fun hashPassword(password: String): String
    fun verifyHashes(password: String, storedPasswordHash: String): Boolean
}

internal class BCryptPasswordHasher(private val kermit: Kermit) : PasswordHasher {
    private val hasher = BCrypt.withDefaults()
    private val verifier = BCrypt.verifyer()

    override fun hashPassword(password: String): String {
        return hasher.hashToString(12, password.toCharArray())
    }

    override fun verifyHashes(password: String, storedPasswordHash: String): Boolean {
        kermit.d { "$password $storedPasswordHash" }
        return verifier.verify(password.toByteArray(), storedPasswordHash.toByteArray()).verified
    }
}