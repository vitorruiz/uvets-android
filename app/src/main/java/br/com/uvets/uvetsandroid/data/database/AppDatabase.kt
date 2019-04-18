package br.com.uvets.uvetsandroid.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.model.Vet

@Database(entities = [Pet::class, Vet::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class, AddressTypeConverter::class, VetServicesTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun vetDao(): VetDao
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

@Dao
interface VetDao {
    @Query("SELECT * FROM vets")
    fun getVets(): LiveData<List<Vet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vet: Vet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vets: List<Vet>)
}