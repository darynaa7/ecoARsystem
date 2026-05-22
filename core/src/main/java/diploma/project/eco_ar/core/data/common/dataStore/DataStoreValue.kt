package diploma.project.eco_ar.core.data.common.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface DataStoreValue<T> {
    val dataStore: DataStore<Preferences>
    val defaultValue: T
    val keyName: String

    suspend fun set(value: T)
    fun get(): Flow<T>
    suspend fun collect(collector: (T) -> Unit)
    suspend fun now(): T
}

class BooleanDataStoreValue(
    override val dataStore: DataStore<Preferences>,
    override val defaultValue: Boolean,
    override val keyName: String
) : DataStoreValue<Boolean> {

    private val key = booleanPreferencesKey(keyName)

    override suspend fun set(value: Boolean) {
        dataStore.edit { it[key] = value }
    }

    override fun get(): Flow<Boolean> {
        return dataStore.data.map { it[key] ?: defaultValue }
    }

    override suspend fun collect(collector: (Boolean) -> Unit) {
        return get().collect(collector)
    }

    override suspend fun now(): Boolean {
        return get().first()
    }
}

class ObjectDataStoreValue<T>(
    override val dataStore: DataStore<Preferences>,
    override val defaultValue: T,
    override val keyName: String,
    private val setter: (T) -> String,
    private val getter: (String?) -> T?
) : DataStoreValue<T> {

    private val key = stringPreferencesKey(keyName)

    override suspend fun set(value: T) {
        dataStore.edit { it[key] = setter(value) }
    }

    override fun get(): Flow<T> {
        return dataStore.data.map { getter(it[key]) ?: defaultValue }
    }

    override suspend fun collect(collector: (T) -> Unit) {
        return get().collect(collector)
    }

    override suspend fun now(): T {
        return get().first()
    }
}