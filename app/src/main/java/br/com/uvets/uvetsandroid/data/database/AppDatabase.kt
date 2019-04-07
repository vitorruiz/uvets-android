package br.com.uvets.uvetsandroid.data.database

import androidx.room.*
import br.com.uvets.uvetsandroid.data.model.Pet
import io.reactivex.Single

@Database(entities = [Pet::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
}

@Dao
interface PetDao {
    @Query("SELECT * FROM pets")
    fun getPets(): Single<List<Pet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pet: Pet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pets: List<Pet>)
}