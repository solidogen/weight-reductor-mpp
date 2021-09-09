package com.spyrdonapps.common.devonly

import com.spyrdonapps.common.model.ApiEndpoints
import com.spyrdonapps.common.model.RefreshTokenRequest
import com.spyrdonapps.common.model.TokenData
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.model.UserCredentials

// This lets me edit backend and keep a single DTO class (even with IDE bugs), as BE module sees only androidMain
// I need this import (...common.devonly.*) for editing in IDE, and real import (...common.model.*) for compiling
typealias Weighing = Weighing
typealias ApiEndpoints = ApiEndpoints
typealias UserCredentials = UserCredentials
typealias TokenData = TokenData
typealias RefreshTokenRequest = RefreshTokenRequest