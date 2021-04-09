package personal.ive.checklistapp.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ivasil on 4/7/2021
 */

private const val TAG = "PreferencesManager"

enum class SortTasks { BY_NAME, BY_DATE }

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore("user_preferences")

    val preferencesFlow = dataStore.data
        .catch { exception ->
            if(exception is IOException) {
                Log.e(TAG, "Error in Preferences manager", exception)
                emit(emptyPreferences())
            }
            else {
                throw exception
            }
        }
        .map { preferences ->
            val sortOrder = SortTasks.valueOf(
                preferences[PreferencesKey.SORT_ORDER] ?: SortTasks.BY_NAME.name
            )
            val hideCompleted = preferences[PreferencesKey.HIDE_COMPLETED] ?: false

            Pair(sortOrder, hideCompleted)
        }

    suspend fun updateSortOrder(sortTasks: SortTasks) = dataStore.edit { preferences ->
        preferences[PreferencesKey.SORT_ORDER] = sortTasks.name
    }

    suspend fun updateHideCompleted(hideCompleted: Boolean) = dataStore.edit { preferences ->
        preferences[PreferencesKey.HIDE_COMPLETED] = hideCompleted
    }

    private object PreferencesKey {
        val SORT_ORDER = preferencesKey<String>("sort_order")
        val HIDE_COMPLETED = preferencesKey<Boolean>("hide_completed")
    }
}