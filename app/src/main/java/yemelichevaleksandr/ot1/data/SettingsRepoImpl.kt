package yemelichevaleksandr.ot1.data

import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.data.room.SettingEntity
import yemelichevaleksandr.ot1.data.room.SettingsDao
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val settingsDao: SettingsDao,
) : SettingsRepo {

    override fun getSetting(): Single<SettingEntity> {
        return settingsDao.getSetting()
    }

    override fun saveSetting(entity: SettingEntity) {
        settingsDao.insertSetting(entity)
    }
}