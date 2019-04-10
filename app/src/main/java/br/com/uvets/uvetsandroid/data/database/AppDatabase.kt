package br.com.uvets.uvetsandroid.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.uvets.uvetsandroid.data.model.Pet

@Database(entities = [Pet::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
}

@Dao
interface PetDao {
    @Query("SELECT * FROM pets")
    fun getPets(): LiveData<List<Pet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pet: Pet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pets: List<Pet>)
}