package yemelichevaleksandr.ot1.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import yemelichevaleksandr.ot1.viewmodel.MainActivityViewModel
import yemelichevaleksandr.ot1.viewmodel.TestActivityViewModel
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        FileStorageModule::class,
        QuestionRepositoryModule::class,
        RoomModule::class,
        SettingsRepoModule::class,
        UpdateRepositoryModule::class,
        UpdaterModule::class,
        MapperModule::class,
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun setContext(context: Context): Builder

        fun build(): ApplicationComponent
    }


    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(testActivityViewModel: TestActivityViewModel)
}