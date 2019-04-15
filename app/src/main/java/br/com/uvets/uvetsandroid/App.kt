package br.com.uvets.uvetsandroid

import android.app.Application
import androidx.room.Room
import br.com.uvets.uvetsandroid.business.AppConfiguration
import br.com.uvets.uvetsandroid.business.AppStorage
import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.business.interfaces.Storage
import br.com.uvets.uvetsandroid.data.database.AppDatabase
import br.com.uvets.uvetsandroid.data.local.AppLocalStorage
import br.com.uvets.uvetsandroid.data.local.LocalStorage
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.data.repository.VetRepository
import br.com.uvets.uvetsandroid.ui.createpet.CreatePetViewModel
import br.com.uvets.uvetsandroid.ui.login.LoginViewModel
import br.com.uvets.uvetsandroid.ui.petlist.PetListViewModel
import br.com.uvets.uvetsandroid.ui.profile.ProfileViewModel
import br.com.uvets.uvetsandroid.ui.signup.SignUpViewModel
import br.com.uvets.uvetsandroid.ui.splash.SplashViewModel
import br.com.uvets.uvetsandroid.ui.vetlist.VetListViewModel
import br.com.uvets.uvetsandroid.utils.AppLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppLogger.init()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }

    private val appModule = module {
        single { Room.databaseBuilder(get(), AppDatabase::class.java, "uvets-database").build() }
        single<LocalStorage> { AppLocalStorage(get()) }
        single<Storage> { AppStorage(get(), get()) }
        single<Configuration> { AppConfiguration(get()) }

        // Repositories
        single { UserRepository(get()) }
        single { PetRepository(get()) }
        single { VetRepository(get()) }

        // View Models
        viewModel { SplashViewModel(get()) }
        viewModel { LoginViewModel(get()) }
        viewModel { SignUpViewModel(get()) }
        viewModel { CreatePetViewModel(get(), get()) }
        viewModel { PetListViewModel(get(), get()) }
        viewModel { VetListViewModel(get(), get()) }
        viewModel { ProfileViewModel(get()) }
    }
}