package schema

import com.spyrdonapps.weightreductor.backend.appModule
import com.spyrdonapps.weightreductor.backend.database.DatabaseSettings
import com.spyrdonapps.weightreductor.backend.deletelater.Weighing
import com.spyrdonapps.weightreductor.backend.di.backendModule
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import com.spyrdonapps.weightreductor.backend.util.utils.AppRunMode
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class SchemaValidationTest : KoinTest {

    private val repo: WeighingsRepository by inject()

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
     * This is called with every localDeployBackend script call.
     * Won't be called on CI, but I guess I will not ever change schema and not run the server locally before pushing.
     * May be called as pre-commit hook if this is not enough, should be fast enough
     * */
    @Test
    fun testSchemaValidationAfterMigrations() {
        if (DatabaseSettings.remoteJdbcUrl != null) {
            // don't break the build if heroku
            return
        }
        withTestApplication({
            appModule(appRunMode = AppRunMode.UnitTesting)
        }) {
            runBlocking {
                repo.upsert(Weighing(weight = 40f, date = Instant.DISTANT_FUTURE))
                val weighings = repo.getAllWeighings()
                assert(weighings.isNotEmpty())
            }
        }
    }
}