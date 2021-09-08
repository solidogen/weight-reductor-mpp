package com.spyrdonapps.weightreductor.backend.database

import com.spyrdonapps.weightreductor.backend.util.utils.AppRunMode
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.koin.core.component.KoinComponent
import javax.sql.DataSource

object DatabaseSettings : KoinComponent {

    private val remoteJdbcUrl: String?
        get() = System.getenv("JDBC_DATABASE_URL")

    lateinit var dataSource: DataSource
        private set

    lateinit var database: Database
        private set

    val hasJdbcUrlSet = remoteJdbcUrl != null

    fun init(appRunMode: AppRunMode) {
        dataSource = createDataSource(appRunMode)
        database = Database.connect(dataSource)
    }

    private fun createDataSource(appRunMode: AppRunMode): DataSource {
        val hikariConfig: HikariConfig = if (remoteJdbcUrl != null) {
            HikariConfig().apply {
                jdbcUrl = remoteJdbcUrl
            }
        } else {
            val url = when (appRunMode) {
                AppRunMode.Default -> "jdbc:h2:mem:test" // "jdbc:postgresql:weightreductor?user=postgres" TODO read option from script variable
                AppRunMode.UnitTesting -> "jdbc:postgresql:weightreductorunittests?user=postgres"
            }
            HikariConfig().apply {
                jdbcUrl = url
            }
        }
        hikariConfig.validate()
        return HikariDataSource(hikariConfig)
    }
}