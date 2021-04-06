package personal.ive.checklistapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import personal.ive.checklistapp.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by ivasil on 4/4/2021
 */

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(private val database: Provider<TaskDatabase>, @ApplicationScope private val applicationScope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insertTask(Task("Wash the dishes"))
                dao.insertTask(Task("Do the laundry"))
                dao.insertTask(Task("Buy groceries", isImportant = true))
                dao.insertTask(Task("Prepare food", isCompleted = true))
                dao.insertTask(Task("Call mom"))
                dao.insertTask(Task("Visit grandma", isCompleted = true))
                dao.insertTask(Task("Repair my bike"))
                dao.insertTask(Task("Call Elon Musk"))
            }
        }
    }
}