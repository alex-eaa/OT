package yemelichevaleksandr.ot1.model.local.room

import yemelichevaleksandr.ot1.model.Question


fun listQuestionToEntity(questions: List<Question>) =
    questions.mapIndexed { index, it -> questionToEntity(index, it) }


fun questionToEntity(index: Int, question: Question) = QuestionEntity(
    id = index,
    question = question.question,
    info = question.info,
    answer0 = question.answersList[0],
    answer1 = question.answersList[1],
    answer2 = question.answersList[2],
    answer3 = question.answersList[3],
    answer4 = question.answersList[4]
)

fun entityToQuestion(entity: QuestionEntity) = Question(
    question = entity.question,
    info = entity.info,
    answersList = arrayListOf(
        entity.answer0,
        entity.answer1,
        entity.answer2,
        entity.answer3,
        entity.answer4,
    ),
)