package yemelichevaleksandr.ot1.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionEntity(
    @PrimaryKey
    val id: Int,
    val question: String,
    val info: String,
    val answer0: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
)