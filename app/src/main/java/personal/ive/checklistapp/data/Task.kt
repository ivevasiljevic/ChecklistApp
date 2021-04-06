package personal.ive.checklistapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

/**
 * Created by ivasil on 4/4/2021
 */

@Entity(tableName="task_table")
@Parcelize
data class Task(val name: String, val isCompleted: Boolean = false, val isImportant: Boolean = false, val dateCreated: Long = System.currentTimeMillis(), @PrimaryKey(autoGenerate = true) val id: Int = 0) : Parcelable {

    val dateCreatedFormatted: String
        get() = DateFormat.getDateTimeInstance().format(dateCreated)
}
