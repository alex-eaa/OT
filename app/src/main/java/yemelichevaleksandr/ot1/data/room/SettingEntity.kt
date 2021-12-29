package yemelichevaleksandr.ot1.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SettingEntity(
    @PrimaryKey
    val id: Long = 0,
    val fileName: String,
    val timeStamp: Long
    )