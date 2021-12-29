package yemelichevaleksandr.ot1.data

import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.App
import yemelichevaleksandr.ot1.data.room.SettingEntity

class SettingsRepoImpl : SettingsRepo {

    override fun getSetting(): Single<SettingEntity> {
        return App.getSettingsDao().getSetting()
    }

    override fun saveSetting(entity: SettingEntity) {
        App.getSettingsDao().insertSetting(entity)
    }
}