package yemelichevaleksandr.ot1.data.room

import androidx.room.*
import io.reactivex.rxjava3.core.Single

@Dao
interface QuestionDao {
    @Query("SELECT * FROM QuestionEntity ORDER BY RANDOM() LIMIT :limit")
    fun getQuestions(limit: Int): Single<List<QuestionEntity>>


    @Transaction
    fun updateAll(entity: List<QuestionEntity>): Boolean {
        deleteAll()
        insertAll(entity)
        return true
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entity: List<QuestionEntity>)

    @Query("DELETE FROM QuestionEntity")
    fun deleteAll()



    @Query("SELECT * FROM SettingEntity WHERE id = 0")
    fun getSetting(): Single<SettingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSetting(entity: SettingEntity)
}