package yemelichevaleksandr.ot1.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface SettingsDao {

    @Query("SELECT * FROM SettingEntity WHERE id = 0")
    fun getSetting(): Single<SettingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSetting(entity: SettingEntity)
}