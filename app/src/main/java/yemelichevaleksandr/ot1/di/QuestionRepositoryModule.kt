package yemelichevaleksandr.ot1.di

import dagger.Module
import dagger.Provides
import yemelichevaleksandr.ot1.data.QuestionRepository
import yemelichevaleksandr.ot1.data.QuestionRepositoryImpl
import yemelichevaleksandr.ot1.data.mappers.MapperForQuestion
import yemelichevaleksandr.ot1.data.room.QuestionDao

@Module
class QuestionRepositoryModule {

    @Provides
    fun provideQuestionRepository(
        questionDao: QuestionDao,
        mapper: MapperForQuestion,
    ): QuestionRepository =
        QuestionRepositoryImpl(questionDao, mapper)
}