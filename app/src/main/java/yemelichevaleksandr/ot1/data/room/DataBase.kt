package yemelichevaleksandr.ot1.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuestionEntity::class, SettingEntity::class], version = 2, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun settingsDao(): SettingsDao
}