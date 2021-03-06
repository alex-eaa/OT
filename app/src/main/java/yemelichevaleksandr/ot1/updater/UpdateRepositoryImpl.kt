package yemelichevaleksandr.ot1.updater

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.Const.NUMBER_QUESTIONS_ITEMS
import yemelichevaleksandr.ot1.Const.PATTERN_QUESTION_BLOCK
import yemelichevaleksandr.ot1.Const.PATTERN_QUESTION_ITEMS
import yemelichevaleksandr.ot1.Const.PATTERN_VERSION_FILE
import yemelichevaleksandr.ot1.data.Question
import yemelichevaleksandr.ot1.data.fileStorage.FileStorage
import javax.inject.Inject

class UpdateRepositoryImpl
@Inject constructor(
    private val fileStorage: FileStorage,
) : UpdateRepository {

    override fun getFilenameWithLatestQuestions(): Single<String> = fileStorage.getListAllFiles()
        .map {
            var version = 0
            var fileNameLatestVersion = ""
            it.forEach { file ->
                Regex(PATTERN_VERSION_FILE).find(file.name)?.let { fileName ->
                    if (fileName.groupValues[1].toInt() > version) {
                        version = fileName.groupValues[1].toInt()
                        fileNameLatestVersion = fileName.groupValues[0]
                    }
                }
            }

            if (fileNameLatestVersion.isEmpty())
                error("Файлы с вопросами в FirebaseStorage не найдены")

            fileNameLatestVersion
        }

    override fun getNewQuestions(fileName: String): Single<List<Question>> {
        return fileStorage.downloadFile(fileName)
            .flatMap {
                parseIntoQuestions(it)
            }
    }

    private fun parseIntoQuestions(data: String): Single<List<Question>> {
        return parseIntoBlocksQuestions(data)
            .skip(NUMBER_QUESTIONS_ITEMS.toLong())
            .buffer(NUMBER_QUESTIONS_ITEMS)
            .map {
                Question(
                    question = it[1],
                    info = it[2],
                    answersList = arrayListOf(it[3], it[4], it[5], it[6], it[7])
                )
            }
            .toList()
    }

    private fun parseIntoBlocksQuestions(data: String): Observable<String> {
        return Observable.create { emitter ->
            Regex(PATTERN_QUESTION_BLOCK).findAll(data)
                .map { Regex(PATTERN_QUESTION_ITEMS).findAll(it.groupValues[0]) }
                .forEach {
                    it.forEach {
                        emitter.onNext(it.groupValues[2])
                    }
                }
            emitter.onComplete()
        }
    }
}