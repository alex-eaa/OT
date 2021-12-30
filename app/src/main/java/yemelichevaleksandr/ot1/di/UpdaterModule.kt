package yemelichevaleksandr.ot1.di

import dagger.Module
import dagger.Provides
import yemelichevaleksandr.ot1.data.QuestionRepository
import yemelichevaleksandr.ot1.data.SettingsRepo
import yemelichevaleksandr.ot1.updater.UpdateRepository
import yemelichevaleksandr.ot1.updater.Updater


@Module
class UpdaterModule {

    @Provides
    fun provideUpdater(
        questionRepo: QuestionRepository,
        settingsRepo: SettingsRepo,
        updateRepo: UpdateRepository,
    ): Updater =
        Updater(questionRepo, settingsRepo, updateRepo)
}