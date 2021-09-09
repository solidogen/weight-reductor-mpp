@file:Suppress("UnusedImport")

package com.spyrdonapps.weightreductor.backend.repository

import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.common.model.*
import com.spyrdonapps.weightreductor.backend.datasource.UsersDataSource
import com.spyrdonapps.weightreductor.backend.util.utils.UserId

class UsersRepository(private val usersDataSource: UsersDataSource) {

    suspend fun checkCredentials(userCredentials: UserCredentials): UserId =
        usersDataSource.checkCredentials(userCredentials)

    suspend fun register(userCredentials: UserCredentials): UserId =
        usersDataSource.register(userCredentials)
}