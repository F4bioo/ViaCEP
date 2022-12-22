package com.fappslab.viacep.form.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.viacep.form.R
import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.presentation.extension.toAddressArgs
import com.fappslab.viacep.form.presentation.viewmodel.FormViewAction
import com.fappslab.viacep.form.presentation.viewmodel.FormViewState
import com.fappslab.viacep.lattetools.robot.checks.checkInputTextIsEmpty
import com.fappslab.viacep.remote.stubmockprovider.StubResponse.addressResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class FormFragmentTest {

    @get:Rule
    val formFragmentRobot = FormFragmentRobot()

    private val address = addressResponse.toAddress()
    private val initialState = FormViewState()

    @Test
    fun checkLoadingIsDisplayed_Should_display_loading_When_shouldShowLoading_state_is_true() {
        val expectedState = initialState.copy(shouldShowLoading = true)

        formFragmentRobot
            .givenState { expectedState }
            .whenLaunch()
            .thenCheck { checkLoadingIsDisplayed() }
    }

    @Test
    fun checkLoadingIsNotDisplayed_Should_hide_loading_When_shouldShowLoading_state_is_false() {
        val expectedState = initialState.copy(shouldShowLoading = false)

        formFragmentRobot
            .givenState { expectedState }
            .whenLaunch()
            .thenCheck { checkLoadingIsNotDisplayed() }
    }

    @Test
    fun checkZipcodeInputIsEnabled_Should_enable_input_When_shouldEnableZipcodeInput_is_true() {
        val expectedState = initialState.copy(
            address = address.toAddressArgs(),
            shouldEnableZipcodeInput = true
        )

        formFragmentRobot
            .givenState { expectedState }
            .whenLaunch()
            .thenCheck { checkZipcodeInputIsEnabled() }
    }

    @Test
    fun checkZipcodeInputIsNotEnabled_Should_disable_input_When_shouldEnableZipcodeInput_is_false() {
        val expectedState = initialState.copy(
            address = address.toAddressArgs(),
            shouldEnableZipcodeInput = false
        )

        formFragmentRobot
            .givenState { expectedState }
            .whenLaunch()
            .thenCheck { checkZipcodeInputIsNotEnabled() }
    }

    @Test
    fun checkInputTextHint_Should_display_hints_in_all_inputs_When_show_screen() {
        val expectedHints = mapOf(
            R.id.input_zipcode to "Zip code",
            R.id.input_street to "Street",
            R.id.input_district to "District",
            R.id.input_city to "City",
            R.id.input_state to "State",
            R.id.input_area_code to "Area code"
        )

        formFragmentRobot
            .whenLaunch()
            .thenCheck {
                expectedHints.forEach {
                    checkInputTextHasExactlyTextHint(it.key, expectedHints.getValue(it.key))
                }
            }
    }

    @Test
    fun checkButtonSaveClicked_Should_clear_form_and_check_inputs_are_empty_When_invoke_button_save() {
        val expectedState = initialState.copy(address = address.toAddressArgs())
        val inputFieldIds = listOf(
            R.id.input_zipcode,
            R.id.input_street,
            R.id.input_district,
            R.id.input_city,
            R.id.input_state,
            R.id.input_area_code
        )

        formFragmentRobot
            .givenState { expectedState }
            .givenAction(
                invoke = { onSetLocalAddress() },
                action = { FormViewAction.ClearForm }
            )
            .whenLaunch()
            .thenCheck {
                checkButtonSaveClicked()
            }.thenCheck {
                inputFieldIds.forEach { resId ->
                    checkInputTextIsEmpty(resId)
                }
            }
    }
}

