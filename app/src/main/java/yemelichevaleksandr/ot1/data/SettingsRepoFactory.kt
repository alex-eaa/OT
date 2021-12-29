package yemelichevaleksandr.ot1.data

object SettingsRepoFactory {
    fun create(): SettingsRepo = SettingsRepoImpl()
}