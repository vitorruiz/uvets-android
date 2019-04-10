package br.com.uvets.uvetsandroid.data.local

interface LocalStorage {

    fun <T> putSerialized(
        key: String,
        obj: T,
        level: PersistenceLevel = PersistenceLevel.ERASABLE
    )

    fun <T> getSerialized(
        key: String,
        default: T?,
        clazz: Class<T>
    ): T?

    fun putString(
        key: String,
        value: String?,
        level: PersistenceLevel = PersistenceLevel.ERASABLE
    )

    fun getString(
        key: String,
        default: String?
    ): String?

    //fun secureString(key: String, default: String?)

    fun putStringList(
        key: String,
        value: List<String>,
        level: PersistenceLevel = PersistenceLevel.ERASABLE
    )

    fun getStringList(
        key: String
    ): MutableList<String>

    fun putInt(
        key: String,
        value: Int,
        level: PersistenceLevel = PersistenceLevel.ERASABLE
    )

    fun getInt(key: String, default: Int): Int

    //fun secureInt(key: String, default: Int)

    fun putLong(
        key: String,
        value: Long,
        level: PersistenceLevel = PersistenceLevel.ERASABLE
    )

    fun getLong(key: String, default: Long): Long

    //fun secureLong(key: String, default: Long)

    fun putBoolean(
        key: String,
        value: Boolean,
        level: PersistenceLevel = PersistenceLevel.ERASABLE
    )

    fun getBoolean(
        key: String,
        default: Boolean
    ): Boolean

    //fun secureBoolean(key: String, default: Boolean)

    fun remove(key: String, level: PersistenceLevel = PersistenceLevel.ERASABLE)

    fun clear()

    fun hasKey(key: String, level: PersistenceLevel = PersistenceLevel.ERASABLE): Boolean

    sealed class PersistenceLevel {
        object ERASABLE : PersistenceLevel()
        object SURVIVE_SESSIONS : PersistenceLevel()
    }

}