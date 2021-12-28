package yemelichevaleksandr.ot1.model.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuestionEntity::class, SettingEntity::class], version = 2, exportSchema = false)
abstract class QuestionDataBase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}