package com.fappslab.viacep.local.client

import com.fappslab.viacep.local.database.FormDatabase

class DatabaseImpl(
    private val database: FormDatabase
) : Database<FormDatabase> {

    override fun create(): FormDatabase {
        return database
    }
}
