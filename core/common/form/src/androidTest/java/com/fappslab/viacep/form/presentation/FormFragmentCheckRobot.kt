package com.fappslab.viacep.form.presentation

import androidx.annotation.IdRes
import com.fappslab.viacep.form.R
import com.fappslab.viacep.lattetools.robot.CheckRobot
import com.fappslab.viacep.lattetools.robot.checks.checkButtonClicked
import com.fappslab.viacep.lattetools.robot.checks.checkInputTextHasHint
import com.fappslab.viacep.lattetools.robot.checks.checkIsDisplayed
import com.fappslab.viacep.lattetools.robot.checks.checkIsEnabled
import com.fappslab.viacep.lattetools.robot.checks.checkIsNotDisplayed
import com.fappslab.viacep.lattetools.robot.checks.checkIsNotEnabled


internal class FormFragmentCheckRobot : CheckRobot<FormFragmentCheckRobot> {

    fun checkLoadingIsDisplayed() {
        checkIsDisplayed(R.id.loading)
    }

    fun checkLoadingIsNotDisplayed() {
        checkIsNotDisplayed(R.id.loading)
    }

    fun checkZipcodeInputIsEnabled() {
        checkIsEnabled(R.id.input_zipcode)
    }

    fun checkZipcodeInputIsNotEnabled() {
        checkIsNotEnabled(R.id.input_zipcode)
    }

    fun checkInputTextHasExactlyTextHint(@IdRes resId: Int, expectedHint: String) {
        checkInputTextHasHint(resId, expectedHint)
    }

    fun checkButtonSaveClicked() {
        checkButtonClicked(R.id.button_save)
    }
}