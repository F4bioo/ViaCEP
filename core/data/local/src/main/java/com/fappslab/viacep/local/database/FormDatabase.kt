package com.fappslab.viacep.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fappslab.viacep.local.database.dao.FormDao
import com.fappslab.viacep.local.model.AddressEntity

@Database(
    entities = [AddressEntity::class],
    version = 1
)
abstract class FormDatabase : RoomDatabase() {

    abstract fun formDao(): FormDao
}
