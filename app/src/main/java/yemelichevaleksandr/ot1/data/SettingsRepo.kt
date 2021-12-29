package yemelichevaleksandr.ot1.data

import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.data.room.SettingEntity

interface SettingsRepo {
    fun getSetting(): Single<SettingEntity>
    fun saveSetting(entity: SettingEntity)
}