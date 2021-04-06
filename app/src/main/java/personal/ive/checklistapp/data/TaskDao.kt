package personal.ive.checklistapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by ivasil on 4/4/2021
 */

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    fun getTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}