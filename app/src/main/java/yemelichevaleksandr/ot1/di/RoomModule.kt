package yemelichevaleksandr.ot1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import yemelichevaleksandr.ot1.data.room.QuestionDao
import yemelichevaleksandr.ot1.data.room.DataBase
import yemelichevaleksandr.ot1.data.room.SettingsDao
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Context): DataBase =
        Room.databaseBuilder(app, DataBase::class.java, "Question.db")
//            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideQuestionDao(dataBase: DataBase): QuestionDao =
        dataBase.questionDao()

    @Singleton
    @Provides
    fun provideSettingsDao(dataBase: DataBase): SettingsDao =
        dataBase.settingsDao()
}