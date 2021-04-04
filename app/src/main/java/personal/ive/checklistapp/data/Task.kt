package personal.ive.checklistapp.data

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

/**
 * Created by ivasil on 4/4/2021
 */

@Parcelize
data class Task(@PrimaryKey(autoGenerate = true) val id: Int = 0, val name: String, val isCompleted: Boolean = false, val isImportant: Boolean = false, val dateCreated: Long = System.currentTimeMillis()) : Parcelable {

    val dateCreatedFormatted: String
        get() = DateFormat.getDateTimeInstance().format(dateCreated)
}
