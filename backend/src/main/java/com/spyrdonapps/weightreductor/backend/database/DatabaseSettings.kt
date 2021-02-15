package com.spyrdonapps.weightreductor.backend.database

import org.jetbrains.exposed.sql.Database

object DatabaseSettings {

    val database = run {
        // fixme if else env vars exists etc
        Database.connect(
            url = "jdbc:postgresql://localhost:12346/test",
            driver = "org.postgresql.Driver",
            user = "root",
            password = "your_pwd"
        )
    }
}