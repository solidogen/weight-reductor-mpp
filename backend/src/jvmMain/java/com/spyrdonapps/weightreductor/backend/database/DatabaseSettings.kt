package com.spyrdonapps.weightreductor.backend.database

import com.spyrdonapps.weightreductor.backend.util.utils.AppRunMode
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.h2.tools.Server
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

    var h2server: Server? = null
        private set

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
                // TODO read option from script variable and restore some of those
                AppRunMode.Default -> {
                    // creating tcp server lets me connect to database through dbeaver. embedded h2 does not allow this, as db lives in our app process only.
                    val h2server = Server.createTcpServer("-tcpPort", "9100", "-ifNotExists").start().also {
                        h2server = it
                    }
                    "jdbc:h2:${h2server.url}/mem:test"
                } // "jdbc:postgresql:weightreductor?user=postgres"
                AppRunMode.UnitTesting -> "jdbc:h2:mem:test" // "jdbc:postgresql:weightreductorunittests?user=postgres"
            }
            HikariConfig().apply {
                jdbcUrl = url
            }
        }
        hikariConfig.validate()
        return HikariDataSource(hikariConfig)
    }
}