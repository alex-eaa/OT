package yemelichevaleksandr.ot1

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Settings(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

//    var param1 by prefs.string()
//    var param3 by prefs.string(
//        key = { "KEY_PARAM3" },
//        defaultValue = "default"
//    )

    var version by prefs.int()
    var countQuestions by prefs.int()


    private fun SharedPreferences.string(
        defaultValue: String? = null,
        key: (KProperty<*>) -> String = KProperty<*>::name
    ): ReadWriteProperty<Any, String?> =
        object : ReadWriteProperty<Any, String?> {
            override fun getValue(
                thisRef: Any,
                property: KProperty<*>
            ) = getString(key(property), defaultValue)

            override fun setValue(
                thisRef: Any,
                property: KProperty<*>,
                value: String?
            ) = edit().putString(key(property), value).apply()
        }

    private fun SharedPreferences.int(
        defaultValue: Int = 0,
        key: (KProperty<*>) -> String = KProperty<*>::name
    ): ReadWriteProperty<Any, Int> =
        object : ReadWriteProperty<Any, Int> {
            override fun getValue(
                thisRef: Any,
                property: KProperty<*>
            ) = getInt(key(property), defaultValue)

            override fun setValue(
                thisRef: Any,
                property: KProperty<*>,
                value: Int
            ) = edit().putInt(key(property), value).apply()
        }

    companion object{
        const val PREF_NAME = "settings"
    }
}