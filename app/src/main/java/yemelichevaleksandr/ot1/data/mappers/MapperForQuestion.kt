package yemelichevaleksandr.ot1.data.mappers

import yemelichevaleksandr.ot1.data.Question
import yemelichevaleksandr.ot1.data.room.QuestionEntity


class MapperForQuestion {

    fun map(questions: List<Question>): List<QuestionEntity> =
        questions.mapIndexed { index, it -> map(it, index) }


    fun map(question: Question, index: Int): QuestionEntity = QuestionEntity(
        id = index,
        question = question.question,
        info = question.info,
        answer0 = question.answersList[0],
        answer1 = question.answersList[1],
        answer2 = question.answersList[2],
        answer3 = question.answersList[3],
        answer4 = question.answersList[4]
    )

    fun map(entity: QuestionEntity): Question = Question(
        question = entity.question,
        info = entity.info,
        answersList = arrayListOf(
            entity.answer0,
            entity.answer1,
            entity.answer2,
            entity.answer3,
            entity.answer4,
        )
    )
}