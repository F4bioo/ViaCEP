package com.fappslab.viacep.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_zipcode")
    val zipcode: String,
    @ColumnInfo(name = "street")
    val street: String?,
    @ColumnInfo(name = "district")
    val district: String?,
    @ColumnInfo(name = "city")
    val city: String?,
    @ColumnInfo(name = "state")
    val state: String?,
    @ColumnInfo(name = "areaCode")
    val areaCode: String?
)
