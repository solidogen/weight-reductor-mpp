package schema

import com.spyrdonapps.weightreductor.backend.CiVariables
import com.spyrdonapps.weightreductor.backend.appModule
import com.spyrdonapps.weightreductor.backend.database.DatabaseSettings
import com.spyrdonapps.common.model.*
import com.spyrdonapps.weightreductor.backend.di.backendModule
import com.spyrdonapps.weightreductor.backend.repository.UsersRepository
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import com.spyrdonapps.weightreductor.backend.util.extensions.isCiBuild
import com.spyrdonapps.weightreductor.backend.util.utils.AppRunMode
import io.ktor.application.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.*
import kotlin.test.assertEquals

class SchemaValidationTest : KoinTest {

    private val weighingsRepository: WeighingsRepository by inject()
    private val usersRepository: UsersRepository by inject()
//    private val refreshTokensRepository: RefreshTokensRepository by inject() // todo test

    @Before
    fun before() {
        startKoin {
            modules(backendModule)
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    /**
     * Schema validation
     *
     * Running methods which insert data and then fetch it for all created tables.
     *
     * If schema after flyway migrations does not match entity mappings, those methods will throw.
     * This process is done on a dynamically created test database.
     *
     * Basically, if this test fails, you need to fix your .sql scripts
     * because they don't match your Exposed table definitions.
     *
     * This is called with every localDeployBackend script call AND as pre-commit hook.
     * */
    @Test
    fun testSchemaValidationAfterMigrations() {
        if (CiVariables.isCiBuild) {
            // don't break the build if Github CI
            println("CI build detected, skipping schema validation")
            return
        }
        println("Local build detected, validating database schema. " +
                "If this is not a local build, CI didn't run generateCiVariables.sh script.")
        withTestApplication({
            appModule(appRunMode = AppRunMode.UnitTesting)
        }) {
            runBlocking {
                val currentlyValidatedTables = listOf("flyway_schema_history", "weighings", "users", "refresh_tokens")

                // Testing weighings table schema
                weighingsRepository.upsert(Weighing(weight = 40f, date = Instant.DISTANT_FUTURE))
                val weighings = weighingsRepository.getAllWeighings()
                assert(weighings.isNotEmpty())

                val testUserCredentials = UserCredentials(username = "asd", password = "zxc")
                usersRepository.register(testUserCredentials)
                assert(usersRepository.checkCredentials(testUserCredentials) >= 0)

                /**
                 * Checking if all existing tables are actually being validated.
                 * If I add a table and do not add it to the [currentlyValidatedTables] this will break.
                 **/
                transaction {
                    val schema = DatabaseSettings.dataSource.connection.schema
                    val databaseTableNames = DatabaseSettings.database.dialect.allTablesNames()
                        .map { it.removePrefix("$schema.") }
                    assertEquals(
                        expected = currentlyValidatedTables.map { it.lowercase() }.sorted(),
                        actual = databaseTableNames.map { it.lowercase() }.sorted(),
                        message = "Not all tables are validated in schema validation process, " +
                                "please add missing tables: ${databaseTableNames - currentlyValidatedTables}\n"
                    )
                    println("All tables were successfully validated")
                }
            }
        }
    }
}