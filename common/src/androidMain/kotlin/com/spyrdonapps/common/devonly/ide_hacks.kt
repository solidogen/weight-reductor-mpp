package com.spyrdonapps.common.devonly

import com.spyrdonapps.common.model.ApiEndpoints
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.model.UserCredentials

// This lets me edit backend and keep a single DTO class (even with IDE bugs)
// I need this import (...common.*) for editing, and real import (...common.model.*) for compiling
typealias Weighing = Weighing
typealias ApiEndpoints = ApiEndpoints
typealias UserCredentials = UserCredentials