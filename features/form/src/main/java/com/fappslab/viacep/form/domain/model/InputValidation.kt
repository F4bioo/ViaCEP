package com.fappslab.viacep.form.domain.model

data class InputValidation(
    val isSuccessful: Boolean = false,
    val fieldType: FieldType
) {

    enum class FieldType {
        Street,
        District,
        City,
        State,
        AreaCode
    }
}
