package yemelichevaleksandr.ot1.data.room

import androidx.room.*
import io.reactivex.rxjava3.core.Single

@Dao
interface SettingsDao {

    @Query("SELECT * FROM SettingEntity WHERE id = 0")
    fun getSetting(): Single<SettingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSetting(entity: SettingEntity)
}