package com.fappslab.viacep.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fappslab.viacep.local.model.AddressEntity

@Dao
interface FormDao {

    @Query("SELECT * FROM address WHERE _zipcode = :zipcode ")
    suspend fun getAddress(zipcode: String): AddressEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAddress(address: AddressEntity)

    @Query("SELECT * FROM address")
    suspend fun getAddresses(): List<AddressEntity>

    @Query("DELETE FROM address WHERE _zipcode = :zipcode")
    suspend fun deleteAddress(zipcode: String)
}
