package yemelichevaleksandr.ot1.model.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList

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