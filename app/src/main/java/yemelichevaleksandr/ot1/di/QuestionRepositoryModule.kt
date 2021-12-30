package yemelichevaleksandr.ot1.di

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Observable
import yemelichevaleksandr.ot1.data.Question
import yemelichevaleksandr.ot1.data.QuestionRepository
import yemelichevaleksandr.ot1.data.QuestionRepositoryImpl
import yemelichevaleksandr.ot1.data.room.QuestionDao
import yemelichevaleksandr.ot1.viewmodel.TestActivityViewModel

@Module
class QuestionRepositoryModule {

    @Provides
    fun provideQuestionRepository(questionDao: QuestionDao): QuestionRepository =
        QuestionRepositoryImpl(questionDao)

    @Provides
    fun provideQuestions(questionRepository: QuestionRepository): Observable<Question> =
        questionRepository.getRndQuestions(TestActivityViewModel.NUMBER_QUESTIONS_IN_TEST)
}