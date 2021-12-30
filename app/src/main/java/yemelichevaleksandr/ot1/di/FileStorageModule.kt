package yemelichevaleksandr.ot1.di

import dagger.Module
import dagger.Provides
import yemelichevaleksandr.ot1.data.fileStorage.FileStorage
import yemelichevaleksandr.ot1.data.fileStorage.FirebaseStorageImpl
import javax.inject.Singleton

@Module
class FileStorageModule {

    @Singleton
    @Provides
    fun provideFileStorage(): FileStorage = FirebaseStorageImpl()
}