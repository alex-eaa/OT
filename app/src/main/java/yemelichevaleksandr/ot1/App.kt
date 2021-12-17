package yemelichevaleksandr.ot1

import android.app.Application
import android.content.Context
import androidx.room.Room
import yemelichevaleksandr.ot1.model.local.room.QuestionDao
import yemelichevaleksandr.ot1.model.local.room.QuestionDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: QuestionDataBase? = null
        private const val DB_NAME = "Question.db"

        fun getQuestionDao(): QuestionDao {
            if (db == null) {
                synchronized(QuestionDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            QuestionDataBase::class.java,
                            DB_NAME
                        )
//                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.questionDao()
        }

        fun getContext(): Context? = appInstance?.applicationContext
    }
}