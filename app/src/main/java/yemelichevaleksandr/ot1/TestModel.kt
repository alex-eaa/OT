package yemelichevaleksandr.ot1

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TestModel {

    companion object {
        const val NUMBER_QUESTIONS_IN_TEST = 20
        const val NUMBER_QUESTIONS_ITEMS = 7
    }


    private val array: ArrayList<Question> = ArrayList()
    private var rndQuestionNumbers = mutableSetOf<Int>()

    fun getQuestions(context: Context): ArrayList<Question> {
        val stringArray = context.resources.getStringArray(R.array.test)
        getRndQuestionNumbers(stringArray.size / NUMBER_QUESTIONS_ITEMS)

//        generateArrayAll(stringArray, context)

        var numText = 1
        rndQuestionNumbers.forEach {
            val startQuestion = it * NUMBER_QUESTIONS_ITEMS
            val question = Question(
                question = "$numText. ${stringArray[startQuestion + 0]}",
                info = stringArray[startQuestion + 1],
                answersList = arrayListOf(
                    stringArray[startQuestion + 2],
                    stringArray[startQuestion + 3],
                    stringArray[startQuestion + 4],
                    stringArray[startQuestion + 5],
                    stringArray[startQuestion + 6])
            )
            numText++
            array.add(question)
        }
        return array
    }

    private fun getRndQuestionNumbers(numberAllQuestions: Int) {
        while (rndQuestionNumbers.size < NUMBER_QUESTIONS_IN_TEST) {
            val rnd = (0 until numberAllQuestions).random()
            rndQuestionNumbers.add(rnd)
        }
    }

    fun generateArrayAll(stringArray: Array<String>, context: Context) {
        Log.d("qqq", "size = ${stringArray.size / NUMBER_QUESTIONS_ITEMS}")
        val array: ArrayList<Question> = ArrayList()

        for (n in 0..207) {
            val question = Question(
                question = stringArray[7 * n + 0],
                info = stringArray[7 * n + 1],
                answersList = arrayListOf(
                    stringArray[7 * n + 2],
                    stringArray[7 * n + 3],
                    stringArray[7 * n + 4],
                    stringArray[7 * n + 5],
                    stringArray[7 * n + 6])
            )
            array.add(question)
        }
        var n = 0
//        array.forEach {
//            Log.d("qqq", "Question $n = ${it.question}")
//            n++
//        }

//        val article = Gson().toJson(array).toString()

//        val sharedPref = context.getSharedPreferences("JSON", Context.MODE_PRIVATE)
//        sharedPref.edit().putString("questions", article).apply()

        val json = QuestionsJson.ONE_QUESTIONS_STR_JSON
        val typeToken = object : TypeToken<List<Question>>() {}.type
        val arrayQuestions = Gson().fromJson<List<Question>>(json, typeToken)

//        Log.d("qqq", "Question $n = ${arrayQuestions.question}")
//        Log.d("qqq", "Question $n = ${arrayQuestions.info}")
//        Log.d("qqq", "Question $n = ${arrayQuestions.answersList[0]}")

        arrayQuestions.forEachIndexed { index, question ->
            Log.d("qqq", "Question $index = ${question.question}")
            Log.d("qqq", "Question $index = ${question.info}")
        }

    }

}