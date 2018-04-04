package com.example.t_rex.moonbookshop

import android.content.Context
import com.example.t_rex.moonbookshop.activities.MainActivity
import com.example.t_rex.moonbookshop.data.AppDatabase
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = KodeinBookShopApp::class)
@RunWith(RobolectricTestRunner::class)
class KodeinTest {
    private val appDatabaseMock: AppDatabase = mock(AppDatabase::class.java)

    @Before
    fun setup() {
        getKodeinOverride = fun(applicationContext: Context): Kodein.Module {
            return Kodein.Module {
                bind<AppDatabase>(overrides = true) with singleton { appDatabaseMock }
            }
        }
    }

    @Test
    fun overridesWorks() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()

        // Run any method on injected object
        val injectedDatabase: AppDatabase = activity.appKodein().instance()
        injectedDatabase.bookDao()

        // Make sure actions were done on mock
        verify(appDatabaseMock, times(1)).bookDao()
    }
}
