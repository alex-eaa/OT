package yemelichevaleksandr.ot1.model.local.room

import androidx.room.Entity
import java.util.ArrayList

@Entity
data class QuestionEntity(
    val id: Long,
    var question: String,
    val answersList: ArrayList<String>,
    val info: String,
)