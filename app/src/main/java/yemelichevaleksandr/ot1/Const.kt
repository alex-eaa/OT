package yemelichevaleksandr.ot1

object Const {
    //    Интервал обновления
    const val UPDATE_INTERVAL = 43400000


    //    Паттерн блока вопроса
    const val PATTERN_QUESTION_BLOCK = "<Row[\\d\\D]+?</Row>"

    //    Паттерн строк в блоке вопроса
    const val PATTERN_QUESTION_ITEMS =
        "<Cell ss:StyleID=\".+\"><Data ss:Type=\"(String|Number)\">(.+?)</Data>"

    //    Количество строк в блоке вопросоа
    const val NUMBER_QUESTIONS_ITEMS = 9


    // Паттерн номера версии в имени zip-файла
    const val PATTERN_VERSION_FILE = "questions_v_(\\d+).xml.zip$"

    // Паттерн номера версии в имени файла
//    const val PATTERN_VERSION_FILE = "questions_v_(\\d+).xml$"
}