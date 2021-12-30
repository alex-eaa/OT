package yemelichevaleksandr.ot1.di

import dagger.Module
import dagger.Provides
import yemelichevaleksandr.ot1.data.fileStorage.FileStorage
import yemelichevaleksandr.ot1.updater.UpdateRepository
import yemelichevaleksandr.ot1.updater.UpdateRepositoryImpl

@Module
class UpdateRepositoryModule {

    @Provides
    fun provideUpdateRepository(fileStorage: FileStorage): UpdateRepository =
        UpdateRepositoryImpl(fileStorage)
}