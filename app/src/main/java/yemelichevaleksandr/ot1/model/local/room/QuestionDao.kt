package yemelichevaleksandr.ot1.model.local.room

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface QuestionDao {
    @Query("SELECT * FROM QuestionEntity WHERE id = :id")
    fun getQuestion(id: Int): Single<QuestionEntity>

    @Query("SELECT * FROM QuestionEntity")
    fun getAllQuestions(): Flowable<QuestionEntity>


    @Transaction
    fun updateAll(entity: List<QuestionEntity>) {
        deleteAll()
        insertAll(entity)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entity: List<QuestionEntity>)

    @Query("DELETE FROM QuestionEntity")
    fun deleteAll()

}