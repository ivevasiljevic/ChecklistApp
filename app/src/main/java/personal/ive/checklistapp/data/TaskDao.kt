package personal.ive.checklistapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by ivasil on 4/4/2021
 */

@Dao
interface TaskDao {

    fun getTasks(searchQuery: String, sortOrder: SortTasks, isHideCompletedChecked: Boolean): Flow<List<Task>> =
        when(sortOrder) {
            SortTasks.BY_NAME -> getTasksSortedByName(searchQuery, isHideCompletedChecked)
            SortTasks.BY_DATE -> getTasksSortedByDate(searchQuery, isHideCompletedChecked)
        }

    @Query("SELECT * FROM task_table WHERE (isCompleted != :isHideCompletedChecked OR isCompleted = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY isImportant DESC, name")
    fun getTasksSortedByName(searchQuery: String, isHideCompletedChecked: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE (isCompleted != :isHideCompletedChecked OR isCompleted = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY isImportant DESC, dateCreated")
    fun getTasksSortedByDate(searchQuery: String, isHideCompletedChecked: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}