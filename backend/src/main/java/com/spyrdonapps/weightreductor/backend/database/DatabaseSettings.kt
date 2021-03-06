package com.spyrdonapps.weightreductor.backend.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

object DatabaseSettings {

    lateinit var dataSource: DataSource
        private set

    fun init() {
        dataSource = createDataSource()
        Database.connect(dataSource)
    }

    private fun createDataSource(): DataSource {
        val remoteJdbcUrl = System.getenv("JDBC_DATABASE_URL")
        val hikariConfig: HikariConfig = if (remoteJdbcUrl != null) {
            HikariConfig().apply {
                jdbcUrl = remoteJdbcUrl
            }
        } else {
            HikariConfig().apply {
                driverClassName = "org.h2.Driver"
                jdbcUrl = "jdbc:h2:mem:test"
                maximumPoolSize = 3
                isAutoCommit = false
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            }
        }
        hikariConfig.validate()
        return HikariDataSource(hikariConfig)
    }
}