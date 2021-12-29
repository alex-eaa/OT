package yemelichevaleksandr.ot1.data

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.App
import yemelichevaleksandr.ot1.data.room.SettingEntity
import yemelichevaleksandr.ot1.data.room.entityToQuestion
import yemelichevaleksandr.ot1.data.room.listQuestionToEntity

class SettingsRepoImpl : SettingsRepo {

    override fun getSetting(): Single<SettingEntity> {
        return App.getSettingsDao().getSetting()
    }

    override fun saveSetting(entity: SettingEntity) {
        App.getSettingsDao().insertSetting(entity)
    }
}