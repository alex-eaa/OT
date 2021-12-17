package yemelichevaleksandr.ot1.model.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(QuestionEntity::class), version = 1, exportSchema = false)
abstract class QuestionDataBase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}