package schema

import com.spyrdonapps.weightreductor.backend.appModule
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
     * This will currently fail on heroku as I would have to create a second db there.
     * For now test locally - last resort are pre-commit tests, but this is not desirable
     * */
    @Test
    fun testSchemaValidationAfterMigrations() {
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