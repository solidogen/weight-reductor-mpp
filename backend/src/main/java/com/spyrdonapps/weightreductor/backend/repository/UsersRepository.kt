@file:Suppress("UnusedImport")

package com.spyrdonapps.weightreductor.backend.repository

import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.common.model.*
import com.spyrdonapps.weightreductor.backend.datasource.UsersDataSource

class UsersRepository(private val usersDataSource: UsersDataSource) {
    suspend fun checkCredentials(userCredentials: UserCredentials): Boolean =
        usersDataSource.checkCredentials(userCredentials)
    suspend fun register(userCredentials: UserCredentials) = usersDataSource.register(userCredentials)
}