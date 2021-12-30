package yemelichevaleksandr.ot1.di

import dagger.Module
import dagger.Provides
import yemelichevaleksandr.ot1.data.SettingsRepo
import yemelichevaleksandr.ot1.data.SettingsRepoImpl
import yemelichevaleksandr.ot1.data.room.SettingsDao

@Module
class SettingsRepoModule {

    @Provides
    fun provideSettingsRepo(settingsDao: SettingsDao): SettingsRepo = SettingsRepoImpl(settingsDao)
}