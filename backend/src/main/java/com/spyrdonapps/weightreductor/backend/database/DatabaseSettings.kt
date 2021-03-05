package com.spyrdonapps.weightreductor.backend.database

import com.spyrdonapps.weightreductor.backend.database.tables.WeighingsTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSettings {

    lateinit var database: Database
        private set

    fun init() {
        database = createDatabase()
        transaction {
            SchemaUtils.create(WeighingsTable)
        }
    }

    private fun createDatabase(): Database {
        val remoteJdbcUrl = System.getenv("JDBC_DATABASE_URL")
        return if (remoteJdbcUrl != null) {
            val hikariConfig = HikariConfig().apply {
                jdbcUrl = remoteJdbcUrl
                validate()
            }
            Database.connect(HikariDataSource(hikariConfig))
        } else {
            val hikariConfig = HikariConfig().apply {
                driverClassName = "org.h2.Driver"
                jdbcUrl = "jdbc:h2:mem:test"
                maximumPoolSize = 3
                isAutoCommit = false
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                validate()
            }
            Database.connect(HikariDataSource(hikariConfig))
//            Database.connect(
//                url = "jdbc:postgresql://localhost:12346/test",
//                driver = "org.postgresql.Driver",
//                user = "root",
//                password = "your_pwd"
//            )
        }
    }
}