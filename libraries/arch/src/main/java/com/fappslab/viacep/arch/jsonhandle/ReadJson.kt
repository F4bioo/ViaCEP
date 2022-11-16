package com.fappslab.viacep.arch.jsonhandle

import com.google.gson.Gson
import java.io.BufferedReader

inline fun <reified DataModel> readFromJSONToModel(jsonFileName: String): DataModel =
    jsonFileName.toBufferedReader().use { reader ->
        Gson().fromJson(reader, DataModel::class.java)
    }

inline fun <reified DataModel> readFromModelToJSON(model: DataModel): String =
    Gson().toJson(model)

fun readFromJSONToString(jsonFileName: String): String =
    jsonFileName.toBufferedReader().use { reader ->
        reader?.readText().orEmpty()
    }

fun String.toBufferedReader(): BufferedReader? {
    return Thread.currentThread().contextClassLoader?.getResourceAsStream(this)?.bufferedReader()
}
