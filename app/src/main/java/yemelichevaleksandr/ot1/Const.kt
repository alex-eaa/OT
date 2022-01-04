package yemelichevaleksandr.ot1

object Const {
    // The number of random questions in the test
    const val NUMBER_QUESTIONS_IN_TEST = 2

    //    Update check interval
    const val UPDATE_INTERVAL = 43400000


    //    Question block pattern
    const val PATTERN_QUESTION_BLOCK = "<Row[\\d\\D]+?</Row>"

    //    Pattern of strings in a question block
    const val PATTERN_QUESTION_ITEMS =
        "<Cell ss:StyleID=\".+\"><Data ss:Type=\"(String|Number)\">(.+?)</Data>"

    //    Number of lines in a question block
    const val NUMBER_QUESTIONS_ITEMS = 9


    // Version number pattern in zip file name
    const val PATTERN_VERSION_FILE = "questions_v_(\\d+).xml.zip$"

    // Version number pattern in xml file name
//    const val PATTERN_VERSION_FILE = "questions_v_(\\d+).xml$"
}